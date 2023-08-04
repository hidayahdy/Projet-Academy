package com.talan.academy.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.talan.academy.dto.ResourcesDto;
import com.talan.academy.entities.Lesson;
import com.talan.academy.entities.Resource;
import com.talan.academy.repositories.ResourceRepository;
import com.talan.academy.services.impl.ResourceServiceImpl;

@ExtendWith(MockitoExtension.class)
class ResourceServiceTest {

	@Mock
	ResourceRepository resourceRepository;


	@InjectMocks
	ResourceServiceImpl resourceService;

	@Test
	void getResourceByIdLesson_success() throws Exception {

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
		lenient().when(resourceRepository.findByLessonId(1L)).thenReturn(listResource);
		List<ResourcesDto> listResourceDto = resourceService.findResourceByLessonId(1L);
		assertEquals(listResourceDto.size(), listResource.size());
	}

	
	
	
}
