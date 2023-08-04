package com.talan.academy.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.talan.academy.controllers.SynopsisController;
import com.talan.academy.dto.SynopsisDto;
import com.talan.academy.entities.Lesson;
import com.talan.academy.entities.Synopsis;
import com.talan.academy.helpers.ModelMapperConverter;
import com.talan.academy.services.SynopsisService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SynopsisController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class SynopsisControllerTest {

	@MockBean
	SynopsisService synopsisService;

	@Autowired
	MockMvc mockMvc;
	
	
	@Test
	void testGetSynopsisByLessonId() throws Exception{
		
		Lesson lesson = new Lesson() ;
		lesson.setId(1L);
		Synopsis synopsis = new Synopsis(1L, "Synopsis", "description synopsis", "", lesson);
		when(synopsisService.findSynopsisByLessonId(1L)).thenReturn(ModelMapperConverter.map(synopsis, SynopsisDto.class));
		this.mockMvc.perform(get("/talan/synopsis/{id}",1)).andExpect(status().isOk());
	}
}
