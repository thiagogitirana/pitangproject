package com.pitangproject.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pitangproject.controller.model.OutputError;
import com.pitangproject.controller.model.OutputUser;
import com.pitangproject.entity.User;
import com.pitangproject.exception.BusinessException;
import com.pitangproject.repository.UserRepository;
import com.pitangproject.security.controller.AuthenticationController;
import com.pitangproject.security.dto.UserAuthenticationRequestDTO;
import com.pitangproject.security.dto.UserRequestDTO;
import com.pitangproject.security.encoder.HashEncoder;
import com.pitangproject.security.util.JWTTokenUtil;

@RestController
public class UserController {
	
	@Value("${jwt.header}")
    private String tokenHeader;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JWTTokenUtil jwtTokenUtil;
	
	@Autowired
    @Qualifier("JWTUserDetailsService")
    private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationController authController;

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
	public ResponseEntity<?> signup(@RequestBody User user) {
		try {
			PasswordEncoder encoder = new HashEncoder(); 
			
			validarCampos(user);
			user.setCreatedAt(new Date());
			user.setLastLogin(new Date());
			user.setPassword(encoder.encode(user.getPassword()));
			userRepository.save(user);
			OutputUser outputUser = new OutputUser();
			outputUser.setMessage("OK");
			outputUser.setResult("Sucesso");
			
			UserAuthenticationRequestDTO authenticationRequest = new UserAuthenticationRequestDTO();
			authenticationRequest.setEmail(user.getEmail());
			authenticationRequest.setPassword(user.getPassword());
			
			return authController.createAuthenticationToken(authenticationRequest);
			
			//return new ResponseEntity<>(outputUser, HttpStatus.OK);
		} catch (BusinessException business) {
			OutputError error = new OutputError();
			error.setMessage(business.getMessage());
			error.setErrorCode(business.getCode());
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(path="/me")
    public UserRequestDTO getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        UserRequestDTO user = (UserRequestDTO) userDetailsService.loadUserByUsername(username);
        return user;
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
		if(userRepository.findByEmail(user.getEmail()) != null) {
			throw new BusinessException("E-mail already exists", 3);
		}
	}
	
	
}
