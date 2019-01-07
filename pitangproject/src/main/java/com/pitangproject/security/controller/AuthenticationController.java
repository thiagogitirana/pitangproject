package com.pitangproject.security.controller;

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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pitangproject.controller.model.OutputError;
import com.pitangproject.entity.User;
import com.pitangproject.exception.BusinessException;
import com.pitangproject.repository.UserRepository;
import com.pitangproject.security.dto.UserAuthenticationRequestDTO;
import com.pitangproject.security.dto.UserAuthenticationResponseDTO;
import com.pitangproject.security.dto.UserRequestDTO;
import com.pitangproject.security.util.JWTTokenUtil;


@RestController
public class AuthenticationController {

	@Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("JWTUserDetailsService")
    private UserDetailsService userDetailsService;
    
    @Autowired
	private UserRepository userRepository;
    
    @RequestMapping(path="/signin")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserAuthenticationRequestDTO authenticationRequest) throws BusinessException {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        User user = userRepository.findByEmail(authenticationRequest.getEmail());
        user.setLastLogin(new Date());
        userRepository.save(user);
        
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new UserAuthenticationResponseDTO(token));
    }
    
    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        UserRequestDTO user = (UserRequestDTO) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new UserAuthenticationResponseDTO(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<Object> handleAuthenticationException(BusinessException business) {       
        OutputError error = new OutputError();
		error.setMessage(business.getMessage());
		error.setErrorCode(business.getCode());
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
    
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
}
