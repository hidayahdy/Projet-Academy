package com.talan.academy.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.talan.academy.entities.User;



public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	
	private Long id;

	private String email;

	private String pseudo;
	
	private boolean isEnabled;
	
	private boolean isActivated ; 

	@JsonIgnore
	private String password;
	

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String email, String pseudo, String password,
			Collection<? extends GrantedAuthority> authorities, boolean isEnabled, boolean isActivated) {
		this.id = id;
		this.email = email;
		this.pseudo = pseudo;
		this.password = password;
		this.authorities = authorities;
		this.isEnabled = isEnabled;
		this.isActivated = isActivated ;
	}

	public static UserDetailsImpl build(User user) {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRoles().getName().name()));

		return new UserDetailsImpl(user.getId(), user.getEmail(), user.getPseudo(), user.getPassword(), authorities, user.isEnabled(), user.isActivated());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getPseudo() {
		return pseudo;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isActivated;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return isEnabled;
	}
	


	@Override
	public int hashCode() {
		return Objects.hash(authorities, email, id, password, pseudo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDetailsImpl other = (UserDetailsImpl) obj;
		return Objects.equals(authorities, other.authorities) && Objects.equals(email, other.email)
				&& Objects.equals(id, other.id) && Objects.equals(password, other.password)
				&& Objects.equals(pseudo, other.pseudo);
	}
	
	
	
	
}
