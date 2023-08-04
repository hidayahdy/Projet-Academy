package com.talan.academy.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talan.academy.controllers.ApplicationController;
import com.talan.academy.dto.ApplicationDto;
import com.talan.academy.entities.Application;
import com.talan.academy.entities.Session;
import com.talan.academy.entities.User;
import com.talan.academy.enums.Ediploma;
import com.talan.academy.enums.Esituation;
import com.talan.academy.enums.Especiality;
import com.talan.academy.enums.Estatus;
import com.talan.academy.helpers.ModelMapperConverter;
import com.talan.academy.services.ApplicationService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ApplicationController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class ApplicationControllerTest {

	@MockBean
	ApplicationService appService;

	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("AppsList")
	void getAppsWithPagination() throws Exception {

		List<Application> list = new ArrayList<Application>();
		User user = new User();
		Session session = new Session();
		user.setId(1L);
		session.setId(1L);
		Application app = new Application(1L, Ediploma.ENGINEER, Esituation.SITUATION_SEARCHING, Especiality.CIVIL, 1,
				true, "motivé", "cv1.pdf", "à revoir", "cv4", Estatus.NEW, user, session);
		list.add(app);
		Pageable pageable = PageRequest.of(0, 1).withSort(Sort.by("creationDate").descending());
		Page<Application> page = new PageImpl<>(list, pageable, list.size());
		when(appService.findApplicationsWithPagination(0, 1))
				.thenReturn(page.map(entity -> ModelMapperConverter.map(entity, ApplicationDto.class)));

		this.mockMvc.perform(get("/talan/application/{offset}/{pageSize}", 0, 1)).andExpect(status().isOk());

	}

	@Test

	@DisplayName("AppsListByQuery1")
	void getAppsWithPaginationByQuery() throws Exception {

		List<Application> list = new ArrayList<Application>();
		Application app = new Application();
		app.setId(1L);
		list.add(app);
		Pageable pageable = PageRequest.of(0, 3).withSort(Sort.by("creationDate").descending());
		Page<Application> page = new PageImpl<>(list, pageable, list.size());

		when(appService.getAllApplicationsByQuery("mohamed", "mohamed", 0, 3))
				.thenReturn(page.map(entity -> ModelMapperConverter.map(entity, ApplicationDto.class)));
		this.mockMvc.perform(get("/talan/application").param("query", "mohamed").param("query1", "mohamed")
				.param("page", "0").param("size", "3")).andExpect(status().isOk());

	}

	@Test

	@DisplayName("UserAppsList")
	void getUserAppsWithPagination() throws Exception {

		List<Application> list = new ArrayList<Application>();
		User user = new User();
		user.setId(1L);
		Application app = new Application(1L, Ediploma.ENGINEER, Esituation.SITUATION_SEARCHING, Especiality.CIVIL, 1,

				true, "motivé", "cv1.pdf", "à revoir", "cvDB.pdf", Estatus.ACCEPTED, user, null);

		list.add(app);
		Pageable pageable = PageRequest.of(0, 3).withSort(Sort.by("creationDate").descending());
		;
		Page<Application> page = new PageImpl<>(list, pageable, list.size());
		when(appService.getApplicationByUserId(1L, pageable))
				.thenReturn(page.map(entity -> ModelMapperConverter.map(entity, ApplicationDto.class)));
		this.mockMvc.perform(get("/talan/application/user/1").param("page", "0").param("size", "3"))
				.andExpect(status().isOk());
	}

	@Test

	@DisplayName("UserAppsList")
	void getUserAppsWithIdNotFound() throws Exception {

		List<Application> list = new ArrayList<Application>();
		User user = new User();
		user.setId(1L);
		Application app = new Application(1L, Ediploma.ENGINEER, Esituation.SITUATION_SEARCHING, Especiality.CIVIL, 1,

				true, "motivé", "cv1.pdf", "à revoir", "cvDB.pdf", Estatus.ACCEPTED, user, null);

		list.add(app);
		Pageable pageable = PageRequest.of(0, 3).withSort(Sort.by("creationDate").descending());
		;
		
		when(appService.getApplicationByUserId(1L, pageable)).thenReturn(null);
		this.mockMvc.perform(get("/talan/application/user/18").param("page", "0").param("size", "3"))
				.andExpect(status().isNotFound());
	}

	@Test

	@DisplayName("CancelUserApp")
	void cancelUserApplication() throws Exception {

		List<Application> list = new ArrayList<Application>();
		Application app = new Application();
		app.setStatus(Estatus.CANCELLED);
		list.add(app);
		appService.cancelApplicationById(2L);
		this.mockMvc.perform(get("/talan/application/status/2")).andExpect(status().isOk());

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test

	@DisplayName("UserAppsList")
	void getUserAppsWithPaginationByQuery() throws Exception {

		List<Application> list = new ArrayList<Application>();
		User user = new User();
		user.setId(1L);
		Application app = new Application(1L, Ediploma.ENGINEER, Esituation.SITUATION_SEARCHING, Especiality.CIVIL, 1,

				true, "motivé", "cv1.pdf", "à revoir", "cvDB.pdf", Estatus.ACCEPTED, user, null);

		list.add(app);
		Pageable pageable = PageRequest.of(0, 3).withSort(Sort.by("creationDate").descending());
		Page<Application> page = new PageImpl<>(list, pageable, list.size());
		when(appService.getApplicationByUserId(1L, pageable))
				.thenReturn(page.map(entity -> ModelMapperConverter.map(entity, ApplicationDto.class)));
		this.mockMvc.perform(get("/talan/application/user/1").param("query", "ACCEPTED").param("query1", "ACCEPTED")
				.param("page", "0").param("size", "3")).andExpect(status().isOk());
	}

	@Test
	@DisplayName("UserAppsList")
	void testgetUserAppsWithPagination() throws Exception {

		List<ApplicationDto> list = new ArrayList<ApplicationDto>();
		ApplicationDto app = new ApplicationDto();
		app.setId(1L);
		list.add(app);
		Pageable pageable = PageRequest.of(0, 3).withSort(Sort.by("creationDate").descending());
		Page<ApplicationDto> page = new PageImpl<>(list, pageable, list.size());

		when(appService.getUserApplicationsByQuery(1L, "NEW", "NEW", 0, 3)).thenReturn(page);
		this.mockMvc.perform(get("/talan/application/user/filter/1").param("query", "NEW").param("query1", "NEW")
				.param("page", "0").param("size", "3")).andExpect(status().isOk());
	}

	@Test
	@DisplayName("CancelCheckStatus")
	void cancelCheckApplicationStatus() throws Exception {

		ApplicationDto appDto = new ApplicationDto();
		appDto.setStatus(Estatus.CANCELLED);
		appDto.setId(3L);
		this.mockMvc.perform(
				put("/talan/application/update/3").contentType("application/json").content(asJsonString(appDto)))
				.andExpect(status().isOk());

	}

	@Test

	@DisplayName("VerifyAppStatus")
	void VerifyAppStatus() throws Exception {

		Application app = new Application();
		app.setId(1L);
		app.setStatus(Estatus.NEW);

		when(appService.verifStatutApplication(1L)).thenReturn(false);
		this.mockMvc.perform(get("http://localhost:8080/talan/application/verif/{id}", 1)).andExpect(status().isOk());

	}

	@Test
	void addApplication() throws Exception {

		MockMultipartFile cv = new MockMultipartFile("cv", "hello.pdf", "text/plain",
				"This is the file content".getBytes());

		ApplicationDto applicationDto = new ApplicationDto();
		applicationDto.setId(7L);
		String email = "omar@gmail.com";
		String phone = "58123456";
		String linkedin = "http://www.linkedin.com/omar";
		String address = "SFAX";
		String session = "1";
		when(appService.addNewApplication(email, linkedin, address, phone, applicationDto, session, cv))
				.thenReturn(applicationDto);

		this.mockMvc
				.perform(MockMvcRequestBuilders.multipart("/talan/application").file(cv)
						.param("email", "omar@gmail.com").param("linkedin", "http://www.linkedin.com/omar")
						.param("address", "sfax").param("phone", "58123456").param("diploma", "ENGINEER")
						.param("situation", "SITUATION_WORKER").param("speciality", "CIVIL").param("experience", "1")
						.param("itKnowledge", "false").param("motivation", "je suis motivé").param("session", "1"))
				.andExpect(status().isOk());

	}

}
