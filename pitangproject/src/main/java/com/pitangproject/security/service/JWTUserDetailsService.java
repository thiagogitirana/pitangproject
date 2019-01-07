package com.pitangproject.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pitangproject.entity.User;
import com.pitangproject.repository.UserRepository;
import com.pitangproject.security.JWTUserFactory;

@Service
public class JWTUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException(String.format("Invalid e-mail or password '%s'.", username));
		} else {
			return JWTUserFactory.create(user);
		}
	}

	
	
	

}
