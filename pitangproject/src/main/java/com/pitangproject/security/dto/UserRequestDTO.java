package com.pitangproject.security.dto;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pitangproject.entity.Phone;

public class UserRequestDTO implements UserDetails {

	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private List<Phone> phones;	
	private Date createdAt;
	private Date lastLogin;	

	public UserRequestDTO(long id, String firstName, String lastName, String email, String password, List<Phone> phones,
			Date createdAt, Date lastLogin) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.phones = phones;
		this.createdAt = createdAt;
		this.lastLogin = lastLogin;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	
	@JsonIgnore
	@Override
	public String getPassword() {
		return this.password;
	}

	@JsonIgnore
	@Override
	public String getUsername() {
		return this.email;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	@JsonIgnore
	public long getId() {
		return id;
	}

	@JsonIgnore
	public void setId(long id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
