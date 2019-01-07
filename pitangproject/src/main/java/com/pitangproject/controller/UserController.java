package com.pitangproject.controller;

import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pitangproject.controller.model.OutputError;
import com.pitangproject.entity.User;
import com.pitangproject.exception.BusinessException;
import com.pitangproject.repository.UserRepository;
import com.pitangproject.security.dto.UserAuthenticationRequestDTO;
import com.pitangproject.security.dto.UserAuthenticationResponseDTO;
import com.pitangproject.security.dto.UserRequestDTO;
import com.pitangproject.security.encoder.HashEncoder;
import com.pitangproject.security.util.JWTTokenUtil;

/**
 * Classe responsável por cadastrar, autenticar e buscar usuário
 * 
 * @author Thiago Gitirana
 *
 */
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
	private AuthenticationManager authenticationManager;

	/**
	 * Método responsável por cadastrar o usuário
	 * 
	 * @param user
	 * @return ResponseEntity
	 * @throws BusinessException
	 */
	@RequestMapping(path = "/signup")
	public ResponseEntity<?> signup(@RequestBody User user) throws BusinessException {

		String password = user.getPassword();
		PasswordEncoder encoder = new HashEncoder();

		validarCampos(user);

		user.setCreatedAt(new Date());
		user.setLastLogin(new Date());
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);

		authenticate(user.getEmail(), password);

		final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new UserAuthenticationResponseDTO(token));
	}

	/**
	 * Método responsável por autenticar o usuário
	 * 
	 * @param authenticationRequest
	 * @return ResponseEntity
	 * @throws BusinessException
	 */
	@RequestMapping(path = "/signin")
	public ResponseEntity<?> signin(@RequestBody UserAuthenticationRequestDTO authenticationRequest)
			throws BusinessException {

		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		User user = userRepository.findByEmail(authenticationRequest.getEmail());
		user.setLastLogin(new Date());
		userRepository.save(user);

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new UserAuthenticationResponseDTO(token));
	}

	/**
	 * Método responsável por retornar os dados do usuário autenticado a partir do
	 * token informado na url de requisição
	 * 
	 * @param request
	 * @return UserRequestDTO
	 */
	@RequestMapping(path = "/me")
	public UserRequestDTO me(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader).substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		UserRequestDTO user = (UserRequestDTO) userDetailsService.loadUserByUsername(username);
		return user;
	}

	/**
	 * Método responsável por tratar as exceções de negócio
	 * 
	 * @param business
	 * @return ResponseEntity
	 */
	@ExceptionHandler({ BusinessException.class })
	public ResponseEntity<Object> handleAuthenticationException(BusinessException business) {
		OutputError error = new OutputError();
		error.setMessage(business.getMessage());
		error.setErrorCode(business.getCode());
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Método responsável por autenticar o usuário
	 * 
	 * @param username
	 * @param password
	 * @throws BusinessException
	 */
	private void authenticate(String username, String password) throws BusinessException {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new BusinessException("Missing fields", 1);
		} catch (BadCredentialsException e) {
			throw new BusinessException("Invalid e-mail or password", 2);
		}
	}

	/**
	 * Método responsável por validar os campos obrigatórios do usuário
	 * 
	 * @param user
	 * @throws BusinessException
	 */
	private void validarCampos(User user) throws BusinessException {
		if (user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getEmail().isEmpty()
				|| user.getPassword().isEmpty()) {
			throw new BusinessException("Missing fields", 1);
		}

		if (user.getFirstName().isBlank() || user.getLastName().isBlank() || user.getEmail().isBlank()
				|| user.getPassword().isBlank()) {
			throw new BusinessException("Invalid fields", 2);
		}
		if (userRepository.findByEmail(user.getEmail()) != null) {
			throw new BusinessException("E-mail already exists", 3);
		}
	}

}
