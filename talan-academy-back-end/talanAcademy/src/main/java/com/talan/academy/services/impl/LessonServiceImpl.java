package com.talan.academy.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talan.academy.dto.LessonDto;
import com.talan.academy.helpers.ModelMapperConverter;
import com.talan.academy.repositories.LessonRepository;
import com.talan.academy.services.LessonService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LessonServiceImpl implements LessonService {

	private final LessonRepository lessonRepository;

	@Autowired
	public LessonServiceImpl(LessonRepository lessonRepository) {
		this.lessonRepository = lessonRepository;
	}

	@Override
	public List<LessonDto> getAllLessonByModuleId(Long id) {
		log.info("All lesson with module id = {} ", id);
		return ModelMapperConverter.mapAll(lessonRepository.findByModuleId(id), LessonDto.class);
	}

}
