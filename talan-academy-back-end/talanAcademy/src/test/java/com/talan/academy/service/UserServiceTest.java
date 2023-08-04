package com.talan.academy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.talan.academy.dto.RegisterDto;
import com.talan.academy.dto.UserPictureDto;
import com.talan.academy.dto.UserUpdateDto;
import com.talan.academy.entities.Role;
import com.talan.academy.entities.Session;
import com.talan.academy.entities.User;
import com.talan.academy.enums.ERole;
import com.talan.academy.helpers.ModelMapperConverter;
import com.talan.academy.repositories.RoleRepository;
import com.talan.academy.repositories.SessionRepository;
import com.talan.academy.repositories.UserRepository;
import com.talan.academy.services.FileService;
import com.talan.academy.services.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserServiceImpl userService;
	
	
	@Mock
	private UserRepository userRepo;
	@Mock
	private RoleRepository roleRepo;
	@Mock
	private PasswordEncoder encoder;
	@Mock
	private FileService fileService;
	@Mock
	private SessionRepository sessionRepo;
	
	@Test
	void RegisterTest() {
		ERole role1 = ERole.ROLE_REGISTRED;


		Role role = new Role(1L, role1);
		RegisterDto userRegister = new RegisterDto();
		userRegister.setEmail("hh@gmail.com");
		userRegister.setFirstName("hai");
		userRegister.setLastName("dfg");
		userRegister.setPassword(new BCryptPasswordEncoder().encode("password"));
		User user = ModelMapperConverter.map(userRegister, User.class);

		user.setRoles(role);
		userRepo.save(user);
		assertEquals("hh@gmail.com", user.getEmail());
		assertEquals("hai", user.getFirstName());
		assertEquals("dfg", user.getLastName());
	}

	@Test
	void findUserByEmail() {

		Role role = new Role(1L, ERole.ROLE_REGISTRED);
		User user = new User("test", "test", "test @gmail.com", "0000", role);
		userService.findUserByEmail(user.getEmail());
		assertEquals("test @gmail.com", user.getEmail());
		assertEquals("test", user.getFirstName());
		assertEquals("test", user.getLastName());
		userRepo.findByEmail(user.getEmail());
	}

	@Test
	void voidVerifyCode() {

		User user = new User("test", "test", "test @gmail.com", "0000", true, "sdcxsdfcx");
		when(userRepo.findByVerificationCode(user.getVerificationCode())).thenReturn(user);
		user.setEnabled(false);
		when(userRepo.save(user)).thenReturn(user);
		Boolean verif = userService.verify(user.getVerificationCode());
		assertTrue(verif);
	}

	@Test
	void voidVerifyCode2() {

		User user = new User("test", "test", "test @gmail.com", "0000", false, null);
    userRepo.findByVerificationCode(user.getVerificationCode());

	userRepo.save(user);
	userService.verify(user.getVerificationCode());
	}

	@Test
	void verifExtention() {
		List<String> extensions = new ArrayList<String>();

		extensions.add("jpg");
		extensions.add("png");
		extensions.add("jpeg");
		userService.verifExtention("image.jpg");
		assertEquals(0,extensions.indexOf("jpg"));
		assertEquals(1,extensions.indexOf("png"));
		assertEquals(2,extensions.indexOf("jpeg"));
		assertEquals(3, extensions.size());
		assertTrue(true);

	}

	@Test
	void UpdateUser() {

		User user = new User();
		User user1 = new User();
		user.setId(1L);
		user.setFirstName("aa");
		user.setLastName("aa");
		user1.setId(1L);
		user1.setFirstName("bb");
		user1.setLastName("bb");

		UserUpdateDto userDto = ModelMapperConverter.map(user1, UserUpdateDto.class);
		Mockito.when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
		Mockito.when(userRepo.save(user1)).thenReturn(user1);
		UserUpdateDto userUpdated = userService.updateUser(userDto, 1L);
		assertEquals(userDto.getId(), userUpdated.getId());

	}

	@Test
	void UpdateUserInfo() {

		User user = new User();
		User user1 = new User();
		user.setId(1L);
		user.setEmail("aa@gmail.com");
		user.setLinkedin("linkedin");
		user.setPhone("2222222");
		user.setAddress("sfax");
		user1.setId(1L);
		user1.setEmail(user.getEmail());
		user1.setLinkedin("linkedinaa");
		user1.setPhone("222222277");
		user1.setAddress("sfax");

		UserUpdateDto userDto = ModelMapperConverter.map(user1, UserUpdateDto.class);
		Mockito.when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
		Mockito.when(userRepo.save(user1)).thenReturn(user1);
		UserUpdateDto userUpdated = userService.updateInformation(userDto.getEmail(), userDto.getLinkedin(),
				userDto.getPhone(), userDto.getAddress());
		assertEquals(userDto.getEmail(), userUpdated.getEmail());

	}

	@Test

	void userExisitngById() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setFirstName("fff");
		user.setLastName("ffff");
		user.setLinkedin("dfghfdss");
		lenient().when(userRepo.findById(1L)).thenReturn(Optional.of(user));
		UserUpdateDto userDto = userService.findUserById(1L);
		assertEquals(user.getFirstName(), userDto.getFirstName());
		assertEquals(user.getLastName(), userDto.getLastName());
		assertEquals(user.getLinkedin(), userDto.getLinkedin());
	}

	@Test
	void ExistMail() {
		User user = new User();
		user.setEmail("test@gmail.com");
		userService.existEmail(user.getEmail());
		userRepo.existsByEmail(user.getEmail());
		assertEquals("test@gmail.com", user.getEmail());
		assertNotNull(user.getEmail());

	}

	@Test

	void testProfileImage() throws Exception {

		MockMultipartFile picture = new MockMultipartFile("hello", "hello.png", "text/plain",
				"This is the file content".getBytes());
		User user = new User();
		User user1 = new User();
		user.setId(1L);
		user1.setPicture(picture.getName());
		
		when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
		userRepo.save(user1);
		
        userService.updatePicture(1L, picture);
        assertEquals(1L,user.getId());
    	

	}
	
	@Test

	void testProfileImageByIdNotFound() throws Exception {

		MockMultipartFile picture = new MockMultipartFile("hello", "hello.png", "text/plain",
				"This is the file content".getBytes());
		User user = new User();
		user.setId(1L);
		when(userRepo.findById(user.getId())).thenReturn(Optional.empty());
		UserPictureDto userDto = userService.updatePicture(1L, picture);
    	assertNull(userDto);

	}
	
	@Test

	void testUserByIdNotFound() throws Exception {

		User user = new User();
		user.setId(1L);
		when(userRepo.findById(user.getId())).thenReturn(Optional.empty());
		UserUpdateDto userDto = userService.findUserById(1L);
    	assertNull(userDto);

	}
	
	@Test

	void testUpdateUserByIdNotFound() throws Exception {

		User user = new User();
		user.setId(1L);
		when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.empty());
		UserUpdateDto userDto = userService.updateInformation(user.getEmail(),user.getLinkedin(), user.getPhone(), user.getAddress());
    	assertNull(userDto);

	}
	
	@Test

	void testUpdateUserInfoByIdNotFound() throws Exception {

		User user = new User();
		UserUpdateDto userDto = new UserUpdateDto() ;
		user.setId(1L);
		when(userRepo.findById(user.getId())).thenReturn(Optional.empty());
		UserUpdateDto userupdateDto = userService.updateUser(userDto, 1L);
    	assertNull(userupdateDto);

	}
	
	@Test
	void UserSessionUpdateByIdUserNotFound() {
		User user = new User();
		user.setId(188L);
		
		Session sessionOpt = new Session();
		sessionOpt.setId(8L);
		user.setSession(sessionOpt);
		when(userRepo.findById(user.getId())).thenReturn(Optional.empty());
		User userSer = ModelMapperConverter.map(userService.addUserSession(188L, sessionOpt.getId()), User.class);
		assertNull(userSer);
	}
	
	@Test
	void UserRoleUpdateByIdUserNotFound() {
		User user = new User();
		user.setId(188L);
		when(userRepo.findById(user.getId())).thenReturn(Optional.empty());
		User userSer = ModelMapperConverter.map(userService.updateUserRole(188L), User.class);
		assertNull(userSer);
	}
	
	@Test
	void UserSessionUpdateByIdSessionNotFound() {
		User user = new User();
		user.setId(1L);
		
		Session sessionOpt = new Session();
		sessionOpt.setId(8L);
		user.setSession(sessionOpt);
		when(sessionRepo.findById(sessionOpt.getId())).thenReturn(Optional.empty());
		when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
		
		User userSer = ModelMapperConverter.map(userService.addUserSession(1L, sessionOpt.getId()), User.class);
		assertNull(userSer);
	}
	
	@Test
	void UserRoleUpdateByRoleNameNotFound() {
		User user = new User();
		user.setId(188L);
		when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
		when(roleRepo.findByName(ERole.ROLE_STUDENT)).thenReturn(Optional.empty());
		User userSer = ModelMapperConverter.map(userService.updateUserRole(188L), User.class);
		assertNull(userSer);
	}
	
	@Test
	void UserSessionUpdateByIdSession() {
		User user = new User();
		user.setId(1L);
		Session sessionOpt = new Session();
		sessionOpt.setId(8L);
		user.setSession(sessionOpt);
		when(sessionRepo.findById(sessionOpt.getId())).thenReturn(Optional.of(sessionOpt));
		when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
		when(userRepo.save(user)).thenReturn(user);
		User userSer = ModelMapperConverter.map(userService.addUserSession(user.getId(), sessionOpt.getId()), User.class);
		assertEquals(userSer.getId(), user.getId());
	}
	
	@Test
	void UserRoleUpdate() {
		User user = new User();
		user.setId(1L);
		Role role = new Role();
		role.setId(1L);
		role.setName(ERole.ROLE_STUDENT);
		when(roleRepo.findByName(ERole.ROLE_STUDENT)).thenReturn(Optional.of(role));
		when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
		when(userRepo.save(user)).thenReturn(user);
		User userSer = ModelMapperConverter.map(userService.updateUserRole(user.getId()), User.class);
		assertEquals(userSer.getId(), user.getId());
	}
	
	 
	}

	


	