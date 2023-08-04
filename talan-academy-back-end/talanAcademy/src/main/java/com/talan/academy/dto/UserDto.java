package com.talan.academy.dto;

import java.time.LocalDateTime;

import com.talan.academy.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	private LocalDateTime creationDate;
	
	private Long id ; 

	private String email;
	
	private String firstName;
	
	private String lastName;
	
	private String address;
	
	private String phone;
	
	private String linkedin;
	
	private Role roles;
	
	private boolean activated;
	private String verificationCode;
}
