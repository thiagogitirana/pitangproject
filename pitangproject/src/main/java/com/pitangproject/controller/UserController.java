package com.pitangproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pitangproject.controller.model.OutputUser;
import com.pitangproject.entity.User;
import com.pitangproject.repository.PhoneRepository;
import com.pitangproject.repository.UserRepository;

@Controller
@RequestMapping(path="/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PhoneRepository phoneRepository;

	@GetMapping(path="/findUserByLastName")	
	public ResponseEntity<OutputUser> findUserByLastName(@RequestParam("name") String name) {
		try {
			List<User> users = userRepository.findByLastName(name);
			OutputUser outputUser = new OutputUser();
			outputUser.setMessage("OK");
			outputUser.setResult(users);
			return new ResponseEntity<OutputUser>(outputUser, HttpStatus.OK);
		} catch (Exception e) {
			OutputUser outputUser = new OutputUser();
			outputUser.setMessage(e.getLocalizedMessage());
			outputUser.setResult(e);
			return new ResponseEntity<OutputUser>(outputUser, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path="/addUser")	
	public ResponseEntity<OutputUser> addUser(@RequestBody User user) {
		try {
			userRepository.save(user);
			OutputUser outputUser = new OutputUser();
			outputUser.setMessage("OK");
			outputUser.setResult("Sucesso");
			return new ResponseEntity<OutputUser>(outputUser, HttpStatus.OK);
		} catch (Exception e) {
			OutputUser outputUser = new OutputUser();
			outputUser.setMessage(e.getLocalizedMessage());
			outputUser.setResult(e);
			return new ResponseEntity<OutputUser>(outputUser, HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
