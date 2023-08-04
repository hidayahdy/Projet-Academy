package com.talan.academy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.talan.academy.dto.ApplicationDto;
import com.talan.academy.dto.UserUpdateDto;
import com.talan.academy.entities.Application;
import com.talan.academy.entities.Session;
import com.talan.academy.entities.User;
import com.talan.academy.enums.Ediploma;
import com.talan.academy.enums.Esituation;
import com.talan.academy.enums.Especiality;
import com.talan.academy.enums.Estatus;
import com.talan.academy.helpers.ModelMapperConverter;
import com.talan.academy.repositories.ApplicationRepository;
import com.talan.academy.services.FileService;
import com.talan.academy.services.SessionService;
import com.talan.academy.services.UserService;
import com.talan.academy.services.impl.ApplicationServiceImpl;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

	@Mock
	private UserService userService;
	@Mock
	private ApplicationRepository appRepository;
	@Mock
	private SessionService sessionService;
	@Mock 
	private FileService fileService;

	@InjectMocks
	private ApplicationServiceImpl appService;

	@Test
	void findAllWithPaginationTest() {

		Session session = new Session();
		User user = new User();

		session.setId(1L);
		user.setId(1L);

		Application a1 = new Application(1L, Ediploma.ENGINEER, Esituation.SITUATION_SEARCHING, Especiality.CIVIL, 1,
				true, "motivé", "cv1.pdf", "à revoir", null, Estatus.ACCEPTED, user, session);
		Application a2 = new Application(2L, Ediploma.OTHER, Esituation.SITUATION_WORKER, Especiality.ENERGY, 5, false,
				"pas de motivation", "cv2.pdf", "bien", null, Estatus.CANCELLED, user, session);
		Application a3 = new Application(3L, Ediploma.DOCTORATE, Esituation.SITUATION_SEARCHING, Especiality.HYDRAULICS,
				3, true, "trés motivé", "cv3.pdf", "passable", null, Estatus.NEW, user, session);

		List<Application> list = new ArrayList<Application>();
		list.add(a1);
		list.add(a2);
		list.add(a3);

		Pageable pageable = PageRequest.of(0, 3).withSort(Sort.by("creationDate").descending());
		Page<Application> page = new PageImpl<>(list, pageable, list.size());

		when(appRepository.findAll(pageable)).thenReturn(page);
		List<ApplicationDto> empList = appService.findApplicationsWithPagination(0, 3).getContent();

//      then
		assertEquals(list.size(), empList.size());
		verify(appRepository, times(1)).findAll(pageable);
	}

	@Test
	void findAppsWithPaginationByQueryLastNameTest() {

		Session session = new Session();
		User user = new User();

		session.setId(1L);
		user.setId(1L);
		user.setLastName("aouadi");
		user.setFirstName("mohamed");

		Application a1 = new Application(1L, Ediploma.ENGINEER, Esituation.SITUATION_SEARCHING, Especiality.CIVIL, 1,
				true, "motivé", "cv1.pdf", "à revoir", null, Estatus.ACCEPTED, user, session);

		List<Application> list = new ArrayList<Application>();
		list.add(a1);

		Pageable pageable = PageRequest.of(0, 2).withSort(Sort.by("creationDate").descending());
		Page<Application> page = new PageImpl<>(list, pageable, list.size());

		when(appRepository.findApplicationsQuerySQL("aouad", "aouad", PageRequest.of(0, 2))).thenReturn(page);

		List<ApplicationDto> empList1 = appService.getAllApplicationsByQuery("aouad", "aouad", 0, 2).getContent();

//      then
		assertEquals(list.size(), empList1.size());

		verify(appRepository, times(1)).findApplicationsQuerySQL("aouad", "aouad", PageRequest.of(0, 2));

	}

	@Test
	void findUserAppTest() {

		Session session = new Session();
		User user = new User();
		session.setId(1L);
		user.setId(1L);
		Application a1 = new Application(1L, Ediploma.ENGINEER, Esituation.SITUATION_SEARCHING, Especiality.CIVIL, 1,
				true, "motivé", "cv1.pdf", "à revoir", null, Estatus.ACCEPTED, user, session);
		Application a2 = new Application(2L, Ediploma.OTHER, Esituation.SITUATION_WORKER, Especiality.ENERGY, 5, false,
				"pas de motivation", "cv2.pdf", "bien", null, Estatus.CANCELLED, user, session);
		Application a3 = new Application(3L, Ediploma.DOCTORATE, Esituation.SITUATION_SEARCHING, Especiality.HYDRAULICS,
				3, true, "trés motivé", "cv3.pdf", "passable", null, Estatus.NEW, user, session);
		List<Application> list = new ArrayList<Application>();
		list.add(a1);
		list.add(a2);
		list.add(a3);
		Pageable pageable = PageRequest.of(0, 3);
		Page<Application> page = new PageImpl<>(list, pageable, list.size());
		when(appRepository.findByUserId(1L, pageable)).thenReturn(page);
		List<ApplicationDto> empList = appService.getApplicationByUserId(1L, pageable)
				.map(entity -> ModelMapperConverter.map(entity, ApplicationDto.class)).getContent();
		assertEquals(3, empList.size());
		verify(appRepository, times(1)).findByUserId(1L, pageable);

	}

	@Test
	void testUpdateStatutApplication() throws Exception {
		Session session = new Session();
		User user = new User();
		session.setId(1L);
		user.setId(1L);
		Application app = new Application(1L, Ediploma.ENGINEER, Esituation.SITUATION_SEARCHING, Especiality.CIVIL, 1,
				true, "motivé", "cv1.pdf", "à revoir", null, Estatus.ACCEPTED, user, session);
		Mockito.when(appRepository.findById(1L)).thenReturn(Optional.of(app));
		Mockito.when(appRepository.save((app))).thenReturn(app);
		ApplicationDto appBD = appService.cancelApplicationById(1L);
		assertNotNull(appBD);
		verify(appRepository).save(any(Application.class));
		assertEquals(1L, app.getId());
		assertEquals(Estatus.CANCELLED, app.getStatus());
	}
	
	@Test
	void testCancelApplicationByIdNotFound() throws Exception {
		Session session = new Session();
		User user = new User();
		session.setId(1L);
		user.setId(1L);
		
		when(appRepository.findById(1L)).thenReturn(Optional.empty());
		ApplicationDto appBD = appService.cancelApplicationById(1L);
		assertNull(appBD);
	}

	@Test
	void testUpdateStatutCheckCancel() throws Exception {
		Session session = new Session();
		User user = new User();
		session.setId(1L);
		user.setId(1L);
		// sent from user
		ApplicationDto appDto = new ApplicationDto();
		appDto.setStatus(Estatus.ACCEPTED);
		// to find by id
		Application app = new Application(1L, Ediploma.ENGINEER, Esituation.SITUATION_SEARCHING, Especiality.CIVIL, 1,
				true, "motivé", "cv1.pdf", "à revoir", "cv4", Estatus.NEW, user, session);

		Mockito.when(appRepository.findById(1L)).thenReturn(Optional.of(app));
		Mockito.when(appRepository.save((app))).thenReturn(app);
		ApplicationDto appBD = appService.updateAppStatus(1L, appDto);
		assertNotNull(appBD);
		verify(appRepository).save(any(Application.class));
		assertEquals(1L, app.getId());
		assertEquals(Estatus.ACCEPTED, appBD.getStatus());
		verify(appRepository, times(1)).findById(1L);
		verify(appRepository, times(1)).save(app);

	}
	
	@Test
	void testUpdateStatutByIdNotFound() throws Exception {
		Session session = new Session();
		User user = new User();
		session.setId(1L);
		user.setId(1L);
		
		ApplicationDto appDto = new ApplicationDto();
		appDto.setStatus(Estatus.ACCEPTED);
	
		when(appRepository.findById(1L)).thenReturn(Optional.empty());

		ApplicationDto appBD = appService.updateAppStatus(1L, appDto);
		assertNull(appBD);
		
	}

	@Test
	void findUserAppsWithPaginationTest() {

		Session session = new Session();
		User user = new User();

		session.setId(1L);
		user.setId(1L);
		user.setLastName("ben salem");
		user.setFirstName("hamza");

		Application a1 = new Application(1L, Ediploma.ENGINEER, Esituation.SITUATION_SEARCHING, Especiality.CIVIL, 1,
				true, "motivé", "cv1.pdf", "à revoir", null, Estatus.ACCEPTED, user, session);

		List<Application> list = new ArrayList<Application>();
		list.add(a1);

		Pageable pageable = PageRequest.of(0, 2).withSort(Sort.by("creationDate").descending());
		Page<Application> page = new PageImpl<>(list, pageable, list.size());

		when(appRepository.findByUserId(1L, PageRequest.of(0, 2))).thenReturn(page);

		List<ApplicationDto> empList1 = appService.getApplicationByUserId(1L, PageRequest.of(0, 2))
				.map(entity -> ModelMapperConverter.map(entity, ApplicationDto.class)).getContent();

//  then
		assertEquals(list.size(), empList1.size());

		verify(appRepository, times(1)).findByUserId(1L, PageRequest.of(0, 2));

	}

	@Test
	void testVerifyTrueStatusApplication() throws Exception {
		Session session = new Session();
		User user = new User();
		session.setId(1L);
		user.setId(1L);
		// sent from user
		ApplicationDto appDto = new ApplicationDto();
		appDto.setStatus(Estatus.ACCEPTED);
		// to find by id
		Application app = new Application(1L, Ediploma.ENGINEER, Esituation.SITUATION_SEARCHING, Especiality.CIVIL, 1,
				true, "motivé", "cv1.pdf", "à revoir", "cv4", Estatus.REFUSED, user, session);
		List<Application> list = new ArrayList<Application>();
		list.add(app);
		Mockito.when(appRepository.findByUserId(1L)).thenReturn(list);
		Boolean verify = appService.verifStatutApplication(1L);
		assertEquals(true,verify);
		verify(appRepository, times(1)).findByUserId(1L);
	}

	@Test
	void testVerifyFalseStatusApplication() throws Exception {
		Session session = new Session();
		User user = new User();
		session.setId(1L);
		user.setId(1L);
		// sent from user
		ApplicationDto appDto = new ApplicationDto();
		appDto.setStatus(Estatus.ACCEPTED);
		// to find by id
		Application app = new Application(1L, Ediploma.ENGINEER, Esituation.SITUATION_SEARCHING, Especiality.CIVIL, 1,
				true, "motivé", "cv1.pdf", "à revoir", "cv4", Estatus.NEW, user, session);
		List<Application> list = new ArrayList<Application>();
		list.add(app);
		Mockito.when(appRepository.findByUserId(1L)).thenReturn(list);
		Boolean verify = appService.verifStatutApplication(1L);
		assertEquals(false,verify);
		verify(appRepository, times(1)).findByUserId(1L);
	}

	@Test
	void findUserAppsWithPaginationByQueryTest() {

		Session session = new Session();
		User user = new User();

		session.setId(1L);
		user.setId(1L);

		Application a1 = new Application(1L, Ediploma.ENGINEER, Esituation.SITUATION_SEARCHING, Especiality.CIVIL, 1,
				true, "motivé", "cv1.pdf", "à revoir", null, Estatus.ACCEPTED, user, session);
		a1.setStatus(Estatus.NEW);
		List<Application> list = new ArrayList<Application>();
		list.add(a1);
		Pageable pageable = PageRequest.of(0, 2).withSort(Sort.by("creationDate").descending());
		Page<Application> page = new PageImpl<>(list, pageable, list.size());

		when(appRepository.findUserApplicationsQuerySQL(1L, "NEW", "NEW", PageRequest.of(0, 5))).thenReturn(page);
		List<ApplicationDto> empList1 = appService.getUserApplicationsByQuery(1L, "NEW", "NEW", 0, 5).getContent();

		assertEquals(list.size(), empList1.size());
		verify(appRepository, times(1)).findUserApplicationsQuerySQL(1L, "NEW", "NEW", PageRequest.of(0, 5));

	}

	@Test
	void testAddNewApplication() throws Exception {
		Session session = new Session();
		User user = new User();
		session.setId(1L);
		user.setId(1L);
		String email = "omar@gmail.com";
		String phone = "58123456";
		String linkedin = "http://www.linkedin.com/omar";
		String address = "SFAX";
		user.setEmail("omarshal@gmail.com");
		user.setAddress(address);
		user.setLinkedin(linkedin);
		user.setPhone(phone);
		Application app = new Application(1L, Ediploma.ENGINEER, Esituation.SITUATION_SEARCHING, Especiality.CIVIL, 1,
				true, "motivé", null, "à revoir", null, Estatus.NEW, null, null);
		MultipartFile cv = new MockMultipartFile("cv", "hello.pdf", "text/plain",
				"This is the file content".getBytes());
		when(userService.updateInformation(email, linkedin, phone, address)).thenReturn(ModelMapperConverter.map(user, UserUpdateDto.class));
		appService.addNewApplication(email, linkedin, address, phone,
				ModelMapperConverter.map(app, ApplicationDto.class), "1", cv);
		ArgumentCaptor<Application> argumentCaptor = ArgumentCaptor.forClass(Application.class);
		verify(appRepository).save(argumentCaptor.capture());
		assertThat(argumentCaptor.getValue().toString()).hasToString(app.toString());

	}
	
	@Test
	void testAddNewApplicationByStatutCancel() throws Exception {
		Session session = new Session();
		User user = new User();
		session.setId(1L);
		user.setId(1L);
		String email = "omar@gmail.com";
		String phone = "58123456";
		String linkedin = "http://www.linkedin.com/omar";
		String address = "SFAX";
		user.setEmail("omarshal@gmail.com");
		user.setAddress(address);
		user.setLinkedin(linkedin);
		user.setPhone(phone);
		Application app = new Application(1L, Ediploma.ENGINEER, Esituation.SITUATION_SEARCHING, Especiality.CIVIL, 1,
				true, "motivé", null, "à revoir", null, Estatus.NEW, null, null);
		MultipartFile cv = new MockMultipartFile("cv", "hello.pdf", "text/plain",
				"This is the file content".getBytes());
		List<Application> list = new ArrayList<Application>();
		list.add(app);
		Mockito.when(appRepository.findByUserId(1L)).thenReturn(list);
		Boolean verify = appService.verifStatutApplication(1L);
		assertEquals(false,verify);
		when(userService.updateInformation(email, linkedin, phone, address)).thenReturn(ModelMapperConverter.map(user, UserUpdateDto.class));
		ApplicationDto application =  appService.addNewApplication(email, linkedin, address, phone,
				ModelMapperConverter.map(app, ApplicationDto.class), "1", cv);
		assertNull(application);

	}

}
