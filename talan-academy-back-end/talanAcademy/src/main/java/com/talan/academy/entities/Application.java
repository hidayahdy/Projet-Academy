package com.talan.academy.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.talan.academy.enums.Ediploma;
import com.talan.academy.enums.Esituation;
import com.talan.academy.enums.Especiality;
import com.talan.academy.enums.Estatus;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "application")
public class Application extends AbstractEntity{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "diploma")
	private  Ediploma diploma;
	

	
	@Enumerated(EnumType.STRING)
	@Column(name = "situation")
	private Esituation situation;
	
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "speciality")
	private Especiality speciality;
	

	
	@Column(name = "experience")
	private Integer experience;
	
	@Column(name = "itKnowledge")
	private boolean  itKnowledge;
	
	@Column(name = "motivation")
	private String  motivation;
	
	@Column(name = "cv")
	private String cv;
	
	@Column(name = "cvBd")
	private String cvBd;

	@Column(name = "comment")
	private String comment;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Estatus status;
	

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "session_id")
	private Session session;


}
