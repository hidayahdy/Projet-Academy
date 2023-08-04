package com.talan.academy.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.talan.academy.dto.ApplicationDto;

public interface ApplicationService {

	Page<ApplicationDto> findApplicationsWithPagination(int offset, int pageSize);

	Page<ApplicationDto> getApplicationByUserId(Long id, Pageable pageable);

	ApplicationDto cancelApplicationById(Long id);

	ApplicationDto updateAppStatus(Long id, ApplicationDto application);

	Page<ApplicationDto> getAllApplicationsByQuery(String query, String query1, int page, int size);

	Page<ApplicationDto> getUserApplicationsByQuery(Long id, String query, String query1, int page, int size);

	ApplicationDto addNewApplication(String email, String linkedin, String address, String phone,
			ApplicationDto applicationDto, String session, MultipartFile cvUser) throws IOException;

	boolean verifStatutApplication(Long idUser);
	
	List<Long> findAppsUsersId();
	


}
