package com.talan.academy.services;

import java.util.List;

import com.talan.academy.dto.ResourcesDto;

public interface ResourceService {

	List<ResourcesDto> findResourceByLessonId(Long id) ;
}
