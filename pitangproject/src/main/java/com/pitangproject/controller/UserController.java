package com.pitangproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pitangproject.controller.model.OutputError;
import com.pitangproject.controller.model.OutputUser;
import com.pitangproject.entity.User;
import com.pitangproject.exception.BusinessException;
import com.pitangproject.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(path="/findUserByLastName")	
	public ResponseEntity<OutputUser> findUserByLastName(@RequestParam("name") String name) {
		try {
			List<User> users = userRepository.findByLastName(name);
			OutputUser outputUser = new OutputUser();
			outputUser.setMessage("OK");
			outputUser.setResult(users);
			return new ResponseEntity<OutputUser>(outputUser, HttpStatus.OK);
		} catch (Exception e) {
			OutputUser outputUser = new OutputUser();
			outputUser.setMessage(e.getMessage());
			outputUser.setResult(null);
			return new ResponseEntity<OutputUser>(outputUser, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(path="/signup")	
	public ResponseEntity<Object> signup(@RequestBody User user) {
		try {
			validarCampos(user);
			userRepository.save(user);
			OutputUser outputUser = new OutputUser();
			outputUser.setMessage("OK");
			outputUser.setResult("Sucesso");
			return new ResponseEntity<>(outputUser, HttpStatus.OK);
		} catch (BusinessException business) {
			OutputError error = new OutputError();
			error.setMessage(business.getMessage());
			error.setErrorCode(business.getCode());
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
	}
	
	private void validarCampos(User user) throws BusinessException {
		if (user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getEmail().isEmpty()
				|| user.getPassword().isEmpty()) {
			throw new BusinessException("Missing fields", 1);
		}

		if (user.getFirstName().isBlank() || user.getLastName().isBlank() || user.getEmail().isBlank()
				|| user.getPassword().isBlank()) {
			throw new BusinessException("Invalid fields", 2);
		}
		if(!userRepository.findByEmail(user.getEmail()).isEmpty()) {
			throw new BusinessException("E-mail already exists", 3);
		}
	}
	
	
}
