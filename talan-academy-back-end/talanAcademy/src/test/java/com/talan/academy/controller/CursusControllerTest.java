package com.talan.academy.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talan.academy.dto.CursusDto;
import com.talan.academy.enums.EcursusType;
import com.talan.academy.services.impl.CursusServiceImpl;
import com.talan.academy.services.impl.SessionServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CursusControllerTest {

	@MockBean
	CursusServiceImpl cursusServiceImpl;
	
	@MockBean
	SessionServiceImpl sessionServiceImpl;

	@Autowired
	MockMvc mockMvc;

	

	@Test
	void testGetAllPublicCursusTest() throws Exception {
		List<CursusDto> listCursus = new ArrayList<>();
		CursusDto cursus = new CursusDto(47L, "java", "react1.png", "java", false, EcursusType.PUBLIC, null, null);
		listCursus.add(cursus);
		when(cursusServiceImpl.getAllCursusByTypeAndVisible(EcursusType.PUBLIC, true)).thenReturn(listCursus);
		this.mockMvc.perform(get("/talan/cursus/public")).andExpect(status().isOk());

	}

	@Test
	void testGetAllPublicCursusTestNull() throws Exception {
		List<CursusDto> listCursus = new ArrayList<>();

		when(cursusServiceImpl.getAllCursusByTypeAndVisible(EcursusType.PUBLIC, true)).thenReturn(listCursus);
		this.mockMvc.perform(get("/talan/cursus/public")).andExpect(status().isOk());

	}

	@Test
	void testGetAllCursusTest() throws Exception {
		List<CursusDto> listCursus = new ArrayList<>();
		CursusDto cursus = new CursusDto(47L, "java", "react1.png", "java", false, EcursusType.PUBLIC, null, null);
		listCursus.add(cursus);
		when(cursusServiceImpl.getAllCursus()).thenReturn(listCursus);
		this.mockMvc.perform(get("/talan/cursus/all")).andExpect(status().isOk());

	}

	@Test
	void testAddCursusTest() throws Exception {

		MockMultipartFile cursusPicture = new MockMultipartFile("cursusPicture", "hello.png", "text/plain",
				"This is the file content".getBytes());
		CursusDto applicationDto = new CursusDto();
		applicationDto.setId(7L);
		when(cursusServiceImpl.addCursus(applicationDto, cursusPicture)).thenReturn(applicationDto);
		this.mockMvc.perform(
				MockMvcRequestBuilders.multipart("/talan/cursus").file(cursusPicture).param("name", "cursus java")
						.param("description", "nouvelle cursus").param("type", "PUBLIC").param("keyWords", "cursus"))
				.andExpect(status().isOk());

	}

	@Test
	void testUpdateCursusVisibile() throws Exception {
		CursusDto cursusUpdated = new CursusDto(1L, "java", "java.png", "java", true, EcursusType.PUBLIC, null, null);
		when(cursusServiceImpl.updateCursusVisible(1L, true)).thenReturn(cursusUpdated);
		this.mockMvc.perform(put("/talan/cursus/update/1").param("visible", "true")).andExpect(status().isOk());
	}

	@Test
	void testUpdateCursus() throws Exception {
		CursusDto cursus = new CursusDto(1L, "java", "java.png", "java", false, EcursusType.PUBLIC, null, null);
		CursusDto cursusUpdated = new CursusDto(1L, "java", "java.png", "java", true, EcursusType.PUBLIC, null, null);

		when(cursusServiceImpl.updateCursus(cursus)).thenReturn(cursusUpdated);
		this.mockMvc.perform(put("/talan/cursus/update").contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)
				.content(new ObjectMapper().writeValueAsString(cursus))).andExpect(status().isOk());
	}

	@Test
	void testGetCursusByIdOk() throws Exception {
		CursusDto cursus = new CursusDto(47L, "java", "react1.png", "java", false, EcursusType.PUBLIC, null, null);
		when(cursusServiceImpl.findCursusById(47L)).thenReturn(cursus);
		this.mockMvc.perform(get("/talan/cursus/{id}", 47)).andExpect(status().isOk());
	}
	
	

	@Test
	void testGetCursusByIdNotOk() throws Exception {
		CursusDto cursus = null;
		when(cursusServiceImpl.findCursusById(47L)).thenReturn(cursus);
		this.mockMvc.perform(get("/talan/cursus/{id}", 47)).andExpect(status().isNotFound());
	}

	@Test
	void testGetAllCursusByPagination() throws Exception {

		CursusDto cursus1 = new CursusDto(1L, "java", "java.png", "java", false, EcursusType.PUBLIC, null,null);
		CursusDto cursus2 = new CursusDto(2L, "react", "react.png", "java", false, EcursusType.PUBLIC, null,null);
		CursusDto cursus3 = new CursusDto(3L, "angular", "angular.png", "java", false, EcursusType.PUBLIC, null,null);

		List<CursusDto> listCursus = new ArrayList<>(Arrays.asList(cursus1, cursus2, cursus3));
		Pageable pageable = PageRequest.of(0, 3).withSort(Sort.by("creationDate").descending());
		Page<CursusDto> page = new PageImpl<>(listCursus, pageable, listCursus.size());

		when(cursusServiceImpl.getAllCursusByPagination(0, 3)).thenReturn(page);
		this.mockMvc.perform(get("/talan/cursus/pages").param("page", "0").param("size", "3"))
				.andExpect(status().isOk());

	}

	@Test
	void testGetAllCursusByPaginationNull() throws Exception {

		List<CursusDto> listCursus = new ArrayList<>();
		Pageable pageable = PageRequest.of(0, 3).withSort(Sort.by("creationDate").descending());
		Page<CursusDto> page = new PageImpl<>(listCursus, pageable, listCursus.size());

		when(cursusServiceImpl.getAllCursusByPagination(0, 3)).thenReturn(page);
		this.mockMvc.perform(get("/talan/cursus/pages").param("page", "0").param("size", "3"))
				.andExpect(status().isOk());

	}

	@Test
    void testGetProfileImage() throws Exception {
        this.mockMvc.perform(get("/talan/cursus/image").param("pictureName", "java-cursus.png"))
                .andExpect(status().isOk());
    }

	@Test
	void testDeleteCursusTest() throws Exception {
		CursusDto cursus = new CursusDto(47L, "java", "react1.png", "java", false, EcursusType.PUBLIC, null, null);
		Long id = cursus.getId();
		this.mockMvc.perform(delete("/talan/cursus/delete/" + id)).andExpect(status().isOk());
	}
	

}
