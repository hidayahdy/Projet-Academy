package com.talan.academy.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.talan.academy.dto.ApplicationDto;
import com.talan.academy.entities.Application;
import com.talan.academy.entities.Session;
import com.talan.academy.entities.User;
import com.talan.academy.enums.Estatus;
import com.talan.academy.helpers.ModelMapperConverter;
import com.talan.academy.repositories.ApplicationRepository;
import com.talan.academy.services.ApplicationService;
import com.talan.academy.services.FileService;
import com.talan.academy.services.SessionService;
import com.talan.academy.services.UserService;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	private final ApplicationRepository applicationRepository;

	private final UserService userService;

	private final FileService fileService;

	private final SessionService sessionService;

	public ApplicationServiceImpl(ApplicationRepository appliactionRepository, UserService userService,
			FileService fileService, SessionService sessionService) {
		this.userService = userService;
		this.applicationRepository = appliactionRepository;
		this.fileService = fileService;
		this.sessionService = sessionService;
	}

	@Override
	public Page<ApplicationDto> getApplicationByUserId(Long id, Pageable pageable) {

		return applicationRepository.findByUserId(id, pageable)
				.map(entity -> ModelMapperConverter.map(entity, ApplicationDto.class));

	}

	@Override
	public ApplicationDto cancelApplicationById(Long id) {
		Optional<Application> applicationAmodifierOpt = applicationRepository.findById(id);
		if (applicationAmodifierOpt.isPresent()) {

			Application applicationAmodifier = applicationAmodifierOpt.get();
			applicationAmodifier.setStatus(Estatus.CANCELLED);
			applicationRepository.save(applicationAmodifier);
			return ModelMapperConverter.map(applicationAmodifier, ApplicationDto.class);
		}
		return null;

	}

	@Override
	public Page<ApplicationDto> findApplicationsWithPagination(int offset, int pageSize) {

		return applicationRepository
				.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by("creationDate").descending()))
				.map(entity -> ModelMapperConverter.map(entity, ApplicationDto.class));

	}

	@Override

	public Page<ApplicationDto> getAllApplicationsByQuery(String query, String query1, int page, int size) {

		return applicationRepository.findApplicationsQuerySQL(query, query1, PageRequest.of(page, size))
				.map(entity -> ModelMapperConverter.map(entity, ApplicationDto.class));
	}

	public Page<ApplicationDto> getUserApplicationsByQuery(Long id, String query, String query1, int page, int size) {
		return applicationRepository.findUserApplicationsQuerySQL(id, query, query1, PageRequest.of(page, size))
				.map(entity -> ModelMapperConverter.map(entity, ApplicationDto.class));
	}

	@Override
	public boolean verifStatutApplication(Long id) {
		List<Application> applicationUser = applicationRepository.findByUserId(id);
		for (Application application : applicationUser) {
			if (application.getStatus().equals(Estatus.ACCEPTED) || application.getStatus().equals(Estatus.NEW)
					|| application.getStatus().equals(Estatus.INPROGRESS)
					|| application.getStatus().equals(Estatus.WITHDRAWAL)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ApplicationDto addNewApplication(String email, String linkedin, String address, String phone,
			ApplicationDto applicationDto, String session, MultipartFile cvUser) throws IOException {
		User userUpdate = ModelMapperConverter.map(userService.updateInformation(email, linkedin, phone, address),
				User.class);
		if (!verifStatutApplication(userUpdate.getId())) {
			return null;
		}
		applicationDto.setStatus(Estatus.NEW);

		applicationDto.setSession(
				ModelMapperConverter.map(sessionService.getSessionById(Long.valueOf(session)), Session.class));
		if (FilenameUtils.getExtension(cvUser.getOriginalFilename()).equals("pdf")) {
			applicationDto.setCv(fileService.save(cvUser));
			applicationDto.setCvBd("CV-" + userUpdate.getFirstName() + userUpdate.getLastName() + ".pdf");
		}
		Application applicationSaved = ModelMapperConverter.map(applicationDto, Application.class);

		applicationSaved.setUser(userUpdate);
		return ModelMapperConverter.map(applicationRepository.save(applicationSaved), ApplicationDto.class);
	}

	@Override
	public ApplicationDto updateAppStatus(Long id, ApplicationDto application) {
		Optional<Application> appToModifyOpt = applicationRepository.findById(id);
		if (!appToModifyOpt.isPresent())
			return null;
		Application appToModify = appToModifyOpt.get();
		appToModify.setStatus(application.getStatus());
		if (appToModify.getStatus().equals(Estatus.ACCEPTED)) {
			User user = appToModify.getUser();
			userService.updateUserRole(user.getId());
			userService.addUserSession(user.getId(), appToModify.getSession().getId());
		}
		return ModelMapperConverter.map(applicationRepository.save(appToModify), ApplicationDto.class);
	}

	

	@Override
	public List<Long> findAppsUsersId() {

		return applicationRepository.findDistintUsersId();
	}

	



}
