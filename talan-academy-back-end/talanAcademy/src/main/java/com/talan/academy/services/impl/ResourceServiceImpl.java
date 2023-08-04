package com.talan.academy.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talan.academy.dto.ResourcesDto;
import com.talan.academy.helpers.ModelMapperConverter;
import com.talan.academy.repositories.ResourceRepository;
import com.talan.academy.services.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {
	
	private final ResourceRepository resourceRepository ;
	
	public ResourceServiceImpl(ResourceRepository resourceRepository) {
		this.resourceRepository = resourceRepository;
	}

	@Override
	public List<ResourcesDto> findResourceByLessonId(Long id) {
		return ModelMapperConverter.mapAll(resourceRepository.findByLessonId(id), ResourcesDto.class);
	}

}
