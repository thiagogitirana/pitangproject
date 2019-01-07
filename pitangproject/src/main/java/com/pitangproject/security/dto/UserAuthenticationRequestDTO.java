package com.pitangproject.security.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO de requisição de autenticação
 * 
 * @author Thiago Gitirana
 *
 */
public class UserAuthenticationRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2613695494774125240L;

	@JsonProperty("email")
	private String email;
	@JsonProperty("password")
	private String password;

	public UserAuthenticationRequestDTO() {
		super();
	}

	public UserAuthenticationRequestDTO(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
