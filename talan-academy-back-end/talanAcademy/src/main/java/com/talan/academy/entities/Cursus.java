package com.talan.academy.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.talan.academy.enums.EcursusType;

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
@Table(name = "cursus")
public class Cursus extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name ;
	
	@Column(name="picture")
	private String picture;
	
	@Column(name = "description", length = 500)
	private String description ;
	
	@Column(nullable=false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean visible;
	
	@Enumerated(EnumType.STRING)
	private EcursusType type;
	
	
	private String[] keyWords;

	
	
}
