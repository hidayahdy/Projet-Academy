package com.talan.academy.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

	private String firstName;
	
	private String lastName ; 

	private String email;

	private Set<String> role;

	private String password;

}
