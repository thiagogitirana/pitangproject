package com.pitangproject.security.dto;

import java.io.Serializable;

/**
 * DTO de resposta da solicitação de token
 * 
 * @author Thiago Gitirana
 *
 */
public class UserAuthenticationResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1104055027744168378L;

	private final String token;

	public UserAuthenticationResponseDTO(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

}
