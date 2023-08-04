package com.talan.academy.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.web.servlet.MockMvc;

import com.talan.academy.dto.CursusDto;
import com.talan.academy.dto.SessionDto;
import com.talan.academy.entities.Cursus;
import com.talan.academy.entities.Session;
import com.talan.academy.enums.ESessionStatus;
import com.talan.academy.enums.EcursusType;
import com.talan.academy.services.impl.SessionServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class SessionControllerTest {

	@MockBean
	SessionServiceImpl sessionService;

	@Autowired
	MockMvc mockMvc;

	@Test
	void testGetAllSessionByStatus() throws Exception {
		CursusDto cursusDto = new CursusDto(1L, "java", "java-cursus.png", "cursus java with talan academy", true,
				EcursusType.PUBLIC, null, null);
		SessionDto sessionDto1 = new SessionDto(1L, null, null, ESessionStatus.PLANNED, cursusDto);
		SessionDto sessionDto2 = new SessionDto(2L, null, null, ESessionStatus.PLANNED, cursusDto);

		List<SessionDto> sessionList = new ArrayList<>(Arrays.asList(sessionDto1, sessionDto2));

		when(sessionService.findByStatus()).thenReturn(sessionList);
		this.mockMvc.perform(get("/talan/session")).andExpect(status().isOk());
	}

	@Test
	void testGetAllSessionWithPagination() throws Exception {
		CursusDto cursus = new CursusDto(1L, "java", "java-cursus.png", "cursus java with talan academy", true,
				EcursusType.PUBLIC, null,null);
		SessionDto session1 = new SessionDto(1L, null, null, ESessionStatus.PLANNED, cursus);
		SessionDto session2 = new SessionDto(2L, null, null, ESessionStatus.PLANNED, cursus);
		List<SessionDto> sessionList = new ArrayList<SessionDto>(Arrays.asList(session1, session2));

		Pageable pageable = PageRequest.of(0, 2).withSort(Sort.by("creationDate").descending());
		Page<SessionDto> page = new PageImpl<>(sessionList, pageable, sessionList.size());

		when(sessionService.getAllSessionPagination(0, 2)).thenReturn(page);
		this.mockMvc.perform(get("/talan/session/pages").param("page", "0").param("size", "2"))
				.andExpect(status().isOk());
	}


	@Test
	void testAddSession() throws Exception {
		SessionDto sessionDto = new SessionDto(1L, null, null, ESessionStatus.PLANNED, null);
		when(sessionService.addSession(null, 1L)).thenReturn(sessionDto);
		this.mockMvc.perform(post("/talan/session/add").param("date", "2022-01-02").param("id", "1"))
				.andExpect(status().isOk());
	}

	@Test
	void testGetAllSessionWithPaginationIsEmpty() throws Exception {

		List<SessionDto> sessionList = new ArrayList<>();
		Pageable pageable = PageRequest.of(0, 2).withSort(Sort.by("creationDate").descending());
		Page<SessionDto> page = new PageImpl<>(sessionList, pageable, sessionList.size());
		when(sessionService.getAllSessionPagination(0, 2)).thenReturn(page);
		this.mockMvc.perform(get("/talan/session/pages").param("page", "0").param("size", "2"))
				.andExpect(status().isOk());
	}

	@Test
	void testDeleteSession() throws Exception {
		Cursus cursus = new Cursus(1L, "java", "java-cursus.png", "cursus java with talan academy", true,
				EcursusType.PUBLIC, null);
		Session session = new Session(1L, null, null, ESessionStatus.PLANNED, cursus);
		Long id = session.getId();
		this.mockMvc.perform(delete("/talan/session/delete/" + id)).andExpect(status().isOk());

	}


}
