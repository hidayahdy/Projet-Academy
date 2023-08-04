package com.talan.academy.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.talan.academy.dto.SynopsisDto;
import com.talan.academy.entities.Lesson;
import com.talan.academy.entities.Synopsis;
import com.talan.academy.repositories.SynopsisRepository;
import com.talan.academy.services.impl.SynopsisServiceImpl;

@ExtendWith(MockitoExtension.class)
class SynopsisServiceTest {

	@Mock
	SynopsisRepository synopsisRepository;


	@InjectMocks
	SynopsisServiceImpl synopsisService;

	@Test
	void getSynopsisByIdLesson_success() throws Exception {

		Lesson lesson = new Lesson() ;
		lesson.setId(1L);
		Synopsis synopsis = new Synopsis(1L, "Synopsis", "description synopsis", "", lesson);
		lenient().when(synopsisRepository.findByLessonId(1L)).thenReturn(synopsis);
		SynopsisDto synopsisDto = synopsisService.findSynopsisByLessonId(1L);
		assertEquals(synopsis.getTitle(), synopsisDto.getTitle());
		assertEquals(synopsis.getDescription(), synopsisDto.getDescription());
	}

	
	
	
}
