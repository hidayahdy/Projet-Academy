package com.talan.academy.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
@Table(name = "users" , uniqueConstraints = { 
		@UniqueConstraint(columnNames = "email") }
)
public class User extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@NotBlank
	@Column(name = "firstName")
	private String firstName;
	
	@NotEmpty
	@NotBlank
	@Column(name = "lastName")
	private String lastName;
	
	@Column(name = "pseudo")
	private String pseudo;
	

	@NotEmpty
	@Email
	@Column(name = "email")
	private String email;
	
	@Size(min = 8)
	@NotBlank(message = "New password is mandatory")
	@Column(name = "password")
	private String password;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "registerDate")
	private Date registerDate;
	
	@Column(name = "linkedin")
	private String linkedin;
	
	@Column(name = "picture")
	private String picture;
	
	@Column(name = "verification_code", length = 64)
	private String verificationCode;
	
	@Column 
	private boolean enabled;
	
	@Column 
	private boolean activated;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role roles;
	
	@ManyToOne
	@JoinColumn(name = "session_id")
	private Session session;

	public User(String firstName,String lastName, String email, String password) {
		this.lastName = lastName;
		this.firstName = firstName ;
		this.email = email;
		this.password = password;
	}
	public User(String firstName,String lastName, String email, String password,boolean enabled) {
		this.lastName = lastName;
		this.firstName = firstName ;
		this.email = email;
		this.password = password;
		this.enabled=enabled;
		
	}
	public User(Long id,String firstName,String lastName, String email, String phone, String address,String linkedin) {
		this.id=id;
		this.firstName = firstName ;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.linkedin=linkedin;
	}
	public User(String firstName,String lastName, String email, String password,Role roles) {
		this.lastName = lastName;
		this.firstName = firstName ;
		this.email = email;
		this.password = password;
		this.roles=roles;
	}
	
	public User(String firstName,String lastName, String email, String password,boolean enabled,String verificationCode) {
		this.lastName = lastName;
		this.firstName = firstName ;
		this.email = email;
		this.password = password;
		this.enabled=enabled;
		this.verificationCode=verificationCode;
		
	}


	
}