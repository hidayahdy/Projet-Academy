package com.talan.academy.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.talan.academy.dto.ApplicationDto;
import com.talan.academy.enums.Ediploma;
import com.talan.academy.enums.Esituation;
import com.talan.academy.enums.Especiality;
import com.talan.academy.services.ApplicationService;

@RestController
@RequestMapping("/talan/application")
@CrossOrigin(origins = "http://localhost:4200")
public class ApplicationController {

	private final ApplicationService applicationService;

	@Autowired
	public ApplicationController(ApplicationService applicationServiceImpl) {
		this.applicationService = applicationServiceImpl;
	}

	

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping(value = "update/{id}")

	public ResponseEntity<ApplicationDto> updateAppStatus(@PathVariable long id,
			@RequestBody ApplicationDto application) {

		return new ResponseEntity<>(this.applicationService.updateAppStatus(id, application), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")

	@GetMapping(params = { "query", "query1", "page", "size" })
	public ResponseEntity<Page<ApplicationDto>> getAllApplicationsByQuery(@RequestParam(required = false) String query,
			@RequestParam(required = false) int page, @RequestParam(required = false) int size,
			@RequestParam(required = false) String query1) {
		return new ResponseEntity<>(this.applicationService.getAllApplicationsByQuery(query, query1, page, size),
				HttpStatus.OK);
	}

	@GetMapping("user/{id}")
	@PreAuthorize("hasAuthority('ROLE_REGISTRED')")
	public ResponseEntity<Object> getApplicationByUserId(@PathVariable Long id, @RequestParam int page,
			@RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("creationDate").descending());
		Page<ApplicationDto> applicationDTO = applicationService.getApplicationByUserId(id, pageable);

		if (applicationDTO == null)
			return new ResponseEntity<>("Application with ID " + id + " Not found in DataBase", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(applicationDTO, HttpStatus.OK);
	}

	@GetMapping(value = "status/{id}")
	// @PreAuthorize("hasAuthority('ROLE_REGISTRED')")
	public ResponseEntity<Object> cancelApplication(@PathVariable Long id) {
		return new ResponseEntity<>(applicationService.cancelApplicationById(id), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")

	@GetMapping("/{offset}/{pageSize}")
	public ResponseEntity<Page<ApplicationDto>> getApplicationssWithPagination(@PathVariable int offset,
			@PathVariable int pageSize) {
		Page<ApplicationDto> applicationsWithPagination = this.applicationService.findApplicationsWithPagination(offset,
				pageSize);
		return new ResponseEntity<>(applicationsWithPagination, HttpStatus.OK);
	}

	@GetMapping(value = "verif/{id}")
	public ResponseEntity<Object> verifStatutApplication(@PathVariable Long id) {

		return new ResponseEntity<>(applicationService.verifStatutApplication(id), HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_REGISTRED')")

	public ResponseEntity<Object> addCursus(@RequestParam(value = "email") String email,
			@RequestParam(value = "linkedin") String linkedin, @RequestParam(value = "address") String address,
			@RequestParam(value = "phone") String phone, @RequestParam(value = "diploma") String diploma,
			@RequestParam(value = "situation") String situation, @RequestParam(value = "speciality") String speciality,
			@RequestParam(value = "experience") String experience,
			@RequestParam(value = "itKnowledge") String itKnowledge,
			@RequestParam(value = "motivation") String motivation, @RequestParam(value = "session") String session,
			@RequestParam(value = "cv") MultipartFile cv) throws IOException {

		ApplicationDto application = ApplicationDto.builder().diploma(Ediploma.valueOf(diploma))
				.situation(Esituation.valueOf(situation)).speciality(Especiality.valueOf(speciality))
				.experience(Integer.valueOf(experience)).itKnowledge(Boolean.valueOf(itKnowledge))
				.motivation(motivation).build();
		return new ResponseEntity<>(
				applicationService.addNewApplication(email, linkedin, address, phone, application, session, cv),
				HttpStatus.OK);

	}

	@GetMapping("user/filter/{id}")
	@PreAuthorize("hasAuthority('ROLE_REGISTRED')")
	public ResponseEntity<Page<ApplicationDto>> getUserApplicationsByQuery(@PathVariable Long id,
			@RequestParam(required = false) String query, @RequestParam(required = false) String query1,
			@RequestParam int page, @RequestParam int size) {
		return new ResponseEntity<>(this.applicationService.getUserApplicationsByQuery(id, query, query1, page, size),
				HttpStatus.OK);

	}
	
	@GetMapping("ids")
	public ResponseEntity<List<Long>> getUsersIds() {
		return new ResponseEntity<>(this.applicationService.findAppsUsersId(),
				HttpStatus.OK);

	}

}
