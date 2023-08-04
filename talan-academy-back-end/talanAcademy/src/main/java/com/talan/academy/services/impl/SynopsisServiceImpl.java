package com.talan.academy.services.impl;

import org.springframework.stereotype.Service;

import com.talan.academy.dto.SynopsisDto;
import com.talan.academy.helpers.ModelMapperConverter;
import com.talan.academy.repositories.SynopsisRepository;
import com.talan.academy.services.SynopsisService;

@Service
public class SynopsisServiceImpl implements SynopsisService {
	
	private final SynopsisRepository synopsisRepository ;
	
	public SynopsisServiceImpl(SynopsisRepository synopsisRepository) {
		this.synopsisRepository = synopsisRepository;
	}

	@Override
	public SynopsisDto findSynopsisByLessonId(Long id) {
		return ModelMapperConverter.map(synopsisRepository.findByLessonId(id), SynopsisDto.class);
	}

}
