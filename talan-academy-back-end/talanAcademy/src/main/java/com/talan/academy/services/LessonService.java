package com.talan.academy.services;

import java.util.List;

import com.talan.academy.dto.LessonDto;

public interface LessonService {

	List<LessonDto> getAllLessonByModuleId(Long id) ;
}
