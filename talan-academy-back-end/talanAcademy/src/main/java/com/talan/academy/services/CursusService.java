package com.talan.academy.services;
import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.talan.academy.dto.CursusDto;
import com.talan.academy.enums.EcursusType;

public interface CursusService {
	List<CursusDto> getAllCursus();
	List<CursusDto>  getAllCursusByTypeAndVisible(EcursusType type, Boolean visible);
	CursusDto findCursusById(Long id) ;
	CursusDto addCursus(CursusDto cursusDto, MultipartFile cursusPicture) throws IOException;
	CursusDto updateCursusVisible(Long id, boolean visible);
	void deleteCursus(Long id);
	CursusDto updateCursus(CursusDto cursus);
	Page<CursusDto> getAllCursusByPagination (int page, int size);
	
	
}