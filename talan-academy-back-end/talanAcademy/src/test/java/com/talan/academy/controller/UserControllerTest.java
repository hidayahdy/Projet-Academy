package com.talan.academy.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talan.academy.dto.LoginDto;
import com.talan.academy.dto.RegisterDto;
import com.talan.academy.dto.UserPictureDto;
import com.talan.academy.dto.UserUpdateDto;
import com.talan.academy.entities.User;
import com.talan.academy.repositories.RoleRepository;
import com.talan.academy.services.impl.UserDetailsImpl;
import com.talan.academy.services.impl.UserServiceImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)

class UserControllerTest {

	@Value("${talanacademy.app.jwtSecret}")
	private String jwtSecret;
	@Value("${talanacademy.app.jwtExpirationMs}")
	private int jwtExpirationMs;
	@Autowired
	MockMvc mockMvc;

	@MockBean
	UserServiceImpl userService;
	RoleRepository roleRepo;
	@Autowired
	HttpServletRequest request;
	MultipartFile multipartFile = new MultipartFile() {

		@Override
		public void transferTo(File dest) throws IOException, IllegalStateException {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public long getSize() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getOriginalFilename() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public InputStream getInputStream() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getContentType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public byte[] getBytes() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
	};

	@Test
	void login() throws Exception {
		String email = "foulen@gmail.com";
		String password = "foulen";
		LoginDto login = new LoginDto();
		login.setEmail(email);
		login.setPassword(password);
		this.mockMvc.perform(post("/talan/auth/login").contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)
				.content(new ObjectMapper().writeValueAsString(login))).andExpect(status().isOk());
	}

	
	String generateJwtToken(Authentication authentication) throws Exception {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		String access_token = Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).claim("id", userPrincipal.getId())
				.claim("roles", userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
						.collect(Collectors.toList()))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
		return access_token;
	}

	@Test
	void testUpdatePicture() throws Exception {

		MockMultipartFile picture = new MockMultipartFile("hello", "hello.png", "text/plain",
				"This is the file content".getBytes());
		UserPictureDto user = new UserPictureDto();
		UserPictureDto user1 = new UserPictureDto();
		user.setId(1L);
		user1.setId(1L);
		user1.setPicture("hello");
		when(userService.updatePicture(1L, picture)).thenReturn(user1);
		this.mockMvc.perform((((MockMultipartHttpServletRequestBuilder) MockMvcRequestBuilders.multipart("/talan/auth/picture").param("id","1")).file(picture))
				).andExpect(status().isMethodNotAllowed());
	}

	@Test
	void testUpdateUser() throws Exception {

		UserUpdateDto user = new UserUpdateDto(1L, "test", "test", "test @gmail.com", "0000", "gg", "ff", "ff", null);

		UserUpdateDto user1 = new UserUpdateDto(1L, "test1", "test1", "test @gmail.com", "0000", "gg", "ff", "ff",
				null);
		when(userService.updateUser(user, user.getId())).thenReturn(user1);
		this.mockMvc
				.perform(patch("/talan/auth/" + user1.getId()).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.ALL).content(new ObjectMapper().writeValueAsString(user)))
				.andExpect(status().isOk());

	}

	@Test
	void getUserIdTest() throws Exception {
		User user = new User();
		user.setId(1L);
		Long id = user.getId();
		this.mockMvc.perform(get("/talan/auth/find-by-id/" + id)).andExpect(status().isOk());
	}

	@Test
	void verificationCode() throws Exception {
		User user = new User("test", "test", "test @gmail.com", "0000", true, "sdcxsdfcx");
		String code = user.getVerificationCode();

		this.mockMvc.perform(get("/talan/auth/verify/" + code)).andExpect(status().isOk());
	}

	@Test
	void findByEmailTest() throws Exception {
		User user = new User("test", "test", "test @gmail.com", "0000", true, "sdcxsdfcx");
		String email = user.getEmail();

		this.mockMvc.perform(get("/talan/auth/" + email)).andExpect(status().isOk());
	}

	@Test
	void registerTest() throws Exception {
		RegisterDto user = new RegisterDto();
		user.setEmail("test11111111@gmail.com");
		user.setFirstName("hidaya");
		user.setLastName("hhhhh");
		user.setPassword("iouhnoub");
		user.setRole(null);

		this.mockMvc.perform(post("/talan/auth/register").contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)
				.content(new ObjectMapper().writeValueAsString(user))).andExpect(status().isNotAcceptable());
	}
	
	
	@Test
	void updatePicture() throws Exception {

		MockMultipartFile picture = new MockMultipartFile("picture", "hello.png", "text/plain",
				"This is the file content".getBytes());

		UserPictureDto user = new UserPictureDto() ;
		user.setId(1L);
		when(userService.updatePicture(user.getId(), picture))
				.thenReturn(user);

		this.mockMvc
				.perform(MockMvcRequestBuilders.multipart(HttpMethod.PUT,"/talan/auth").file(picture)
						.param("id", "1")).andExpect(status().isOk());

	}


}