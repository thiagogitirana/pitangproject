package com.pitangproject.security.encoder;

import org.springframework.security.crypto.password.PasswordEncoder;

public class HashEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		Integer newPass = rawPassword.hashCode();
		return newPass.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		
		Integer rawPasswordEncoded = rawPassword.hashCode();
		
		return encodedPassword.equals(rawPasswordEncoded.toString());
	}

}
