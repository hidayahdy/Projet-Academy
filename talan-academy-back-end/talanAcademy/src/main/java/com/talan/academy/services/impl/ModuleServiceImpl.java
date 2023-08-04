package com.talan.academy.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talan.academy.dto.ModuleDto;
import com.talan.academy.helpers.ModelMapperConverter;
import com.talan.academy.repositories.ModuleRepository;
import com.talan.academy.services.LessonService;
import com.talan.academy.services.ModuleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ModuleServiceImpl implements ModuleService {

	private final ModuleRepository moduleRepository;

	private final LessonService lessonService;

	@Autowired
	public ModuleServiceImpl(ModuleRepository moduleRepository, LessonService lessonService) {
		this.moduleRepository = moduleRepository;
		this.lessonService = lessonService;
	}

	@Override
	public List<ModuleDto> getModuleByCursusId(Long id) {
		List<ModuleDto> moduleDtos = ModelMapperConverter.mapAll(moduleRepository.findByCursusId(id), ModuleDto.class);
		moduleDtos
				.forEach(moduleDto -> moduleDto.setLessonDto(lessonService.getAllLessonByModuleId(moduleDto.getId())));
		log.info("list of module with cursus id = {}", id);
		return moduleDtos;
	}

}
