package com.talan.academy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.talan.academy.entities.Lesson;
import com.talan.academy.entities.Module;
import com.talan.academy.helpers.ModelMapperConverter;
import com.talan.academy.repositories.LessonRepository;
import com.talan.academy.services.LessonService;

@SpringBootTest
class LessonServiceTest {

	@Autowired
	private LessonService lessonService;

	@MockBean
	private LessonRepository lessonRepository;

	private Lesson lesson;

	private Lesson lesson2;
	
	private Lesson lesson3;
		
	@BeforeEach
	void setUp() throws Exception {
		Module module = new Module(1L, "Algorithmie Et OO", "Ce module permet aux apprentis de comprendre les fondements de la programmation et l’algorithmie, savoir représenter un algorithme via des schémas et le pseudo-langage et compiler un programme en java. Ensuite, les apprentis découvrent les concepts de la programmation orienté objet et appliqueront ces fondements sur des exemples d''application en Java", null);
		lesson = new Lesson(1L, "lesson1", false, module);
		lesson2 = new Lesson(2L, "lesson2", false, module);
		lesson3 = new Lesson(3L, "lesson3", false, module);
	}

	@Test
	void testListLessonByModuleId() {
			List<Lesson> list = new ArrayList<>() ; 
			list.add(lesson) ; 
			list.add(lesson2) ; 
			list.add(lesson3);
			when(lessonRepository.findByModuleId(1L)).thenReturn(list) ; 
			List<Lesson> authList = ModelMapperConverter.mapAll(lessonService.getAllLessonByModuleId(1L), Lesson.class) ; 
			assertEquals(3, authList.size());
	}

}