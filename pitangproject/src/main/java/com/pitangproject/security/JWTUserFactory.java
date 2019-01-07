package com.pitangproject.security;

import com.pitangproject.entity.User;
import com.pitangproject.security.dto.UserRequestDTO;

public final class JWTUserFactory {

	
	
	private JWTUserFactory() {
	}

	public static UserRequestDTO create(User user) {
		return new UserRequestDTO(user.getId(), user.getFirstName(), user.getLastName(), 
				user.getEmail(), user.getPassword(), user.getPhones(), user.getCreatedAt(), user.getLastLogin());
	}
}
