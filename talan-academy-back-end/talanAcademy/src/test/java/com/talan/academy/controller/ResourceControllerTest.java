package com.talan.academy.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.talan.academy.controllers.ResourceController;
import com.talan.academy.dto.ResourcesDto;
import com.talan.academy.entities.Lesson;
import com.talan.academy.entities.Resource;
import com.talan.academy.helpers.ModelMapperConverter;
import com.talan.academy.services.ResourceService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ResourceController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class ResourceControllerTest {

	@MockBean
	ResourceService resourceService;

	@Autowired
	MockMvc mockMvc;
	
	
	@Test
	void testGetSynopsisByLessonId() throws Exception{
		
		Lesson lesson = new Lesson() ;
		lesson.setId(1L);
		Lesson lesson2 = new Lesson() ;
		lesson2.setId(2L);
		Resource resource1 = new Resource(1L, null, null, 0, lesson);
		Resource resource2 = new Resource(2L, null, null, 0, lesson);
		Resource resource4 = new Resource(4L, null, null, 0, lesson);
		List<Resource> listResource = new ArrayList<>() ;
		listResource.add(resource4);
		listResource.add(resource2);
		listResource.add(resource1);
		when(resourceService.findResourceByLessonId(1L)).thenReturn(ModelMapperConverter.mapAll(listResource, ResourcesDto.class));
		this.mockMvc.perform(get("/talan/resource/{id}",1)).andExpect(status().isOk());
	}

}

