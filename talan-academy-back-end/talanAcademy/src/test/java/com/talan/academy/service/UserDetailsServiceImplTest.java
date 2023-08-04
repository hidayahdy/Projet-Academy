package com.talan.academy.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.talan.academy.entities.Role;
import com.talan.academy.entities.User;
import com.talan.academy.enums.ERole;
import com.talan.academy.repositories.UserRepository;
import com.talan.academy.services.impl.UserDetailsImpl;
import com.talan.academy.services.impl.UserDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

	
	@Mock	
	private UserRepository userRepo;
	
	@InjectMocks
	private UserDetailsServiceImpl userService;
	
	@Test
	void testLoadUserByUsername_success() throws Exception {
		Role role = new Role(1L, ERole.ROLE_REGISTRED);
		User user = new User("test", "test", "test@gmail.com", "0000", role);

		Mockito.when(userRepo.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
		UserDetails userDetails = UserDetailsImpl.build(user);
		UserDetails userResult = userService.loadUserByUsername(user.getEmail());

		assertEquals(userDetails.getUsername(), userResult.getUsername());
	}
	
	@Test
	void testLoadUserByUsername_failed() throws Exception{
		Mockito.when(userRepo.findByEmail("test@gmail.com")).thenReturn(Optional.empty());
		assertThatThrownBy(() -> userService.loadUserByUsername("test@gmail.com")).isInstanceOf(UsernameNotFoundException.class);
	}

}