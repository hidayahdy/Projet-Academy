package com.talan.academy.services;

import com.talan.academy.dto.SynopsisDto;

public interface SynopsisService {

	SynopsisDto findSynopsisByLessonId(Long id) ; 
}
