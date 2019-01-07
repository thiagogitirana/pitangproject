package com.pitangproject.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pitangproject.security.JWTAuthenticationEntryPoint;
import com.pitangproject.security.JWTAuthorizationTokenFilter;
import com.pitangproject.security.encoder.HashEncoder;
import com.pitangproject.security.service.JWTUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JWTAuthenticationEntryPoint unauthorizedHandler;

	@Autowired
	private JWTUserDetailsService jwtUserDetailsService;

	@Autowired
	JWTAuthorizationTokenFilter authenticationTokenFilter;

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoderBean());
	}

	@Bean
	public PasswordEncoder passwordEncoderBean() {
		return new HashEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()

				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				.authorizeRequests().antMatchers(HttpMethod.GET, "/signup").permitAll()
				.antMatchers(HttpMethod.GET, "/signin").permitAll().antMatchers(HttpMethod.POST, "/signup").permitAll()
				.antMatchers(HttpMethod.POST, "/signin").permitAll().antMatchers("/h2-console/**/**").permitAll()
				.antMatchers("/auth/**").permitAll().anyRequest().authenticated();

		http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

		http.headers().frameOptions().sameOrigin().cacheControl();

	}

}
