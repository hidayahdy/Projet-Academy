package com.talan.academy.dto;

import java.io.Serializable;
import java.util.List;

import com.talan.academy.enums.EcursusType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursusDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id ; 
	
	private String name ; 
	
	private String picture;
	
	private String description ;
	
	private boolean visible;
	
	private EcursusType type;
	
	private String[] keyWords;
	
	private List<ModuleDto> moduleDto ;
}
