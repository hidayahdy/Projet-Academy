package com.talan.academy.dto;

import java.time.LocalDateTime;

import com.talan.academy.entities.Session;
import com.talan.academy.entities.User;
import com.talan.academy.enums.Ediploma;
import com.talan.academy.enums.Esituation;
import com.talan.academy.enums.Especiality;
import com.talan.academy.enums.Estatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDto  {
	private LocalDateTime creationDate;

   
    private LocalDateTime lastModifiedDate;
    
   
    private String createdBy;

   
    private String lastModifiedBy;
	private Long id;

	private Ediploma diploma;

	private Esituation situation;

	private Especiality speciality;

	private Integer experience;

	private boolean itKnowledge;

	private String motivation;

	private String cv;

	private String cvBd;

	private String comment;

	private Estatus status;

	private User user;

	private Session session;
}
