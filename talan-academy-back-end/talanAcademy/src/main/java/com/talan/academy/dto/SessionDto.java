package com.talan.academy.dto;

import java.sql.Date;

import com.talan.academy.enums.ESessionStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionDto {

	private Long id;

	private Date startDate;

	private Date endDate;

	private ESessionStatus status;
	
	private CursusDto cursus;
	
}
