package com.talan.academy.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModuleDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name ; 
	
	private String description ; 
	
	private List<LessonDto> lessonDto ;
	
}
