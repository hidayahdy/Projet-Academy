package com.talan.academy.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name ; 
	
}
