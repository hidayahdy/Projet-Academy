package com.talan.academy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
	
	private Long id;

	private String firstName;

	private String lastName;

	private String email;
	
	private String phone;
	
	private String picture;
	
	private String address;
	
	private String linkedin;

	private SessionDto session;
}
