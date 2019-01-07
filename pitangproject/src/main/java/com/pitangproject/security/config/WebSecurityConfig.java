package com.pitangproject.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pitangproject.security.JWTAuthenticationEntryPoint;
import com.pitangproject.security.JWTAuthenticationFilter;
import com.pitangproject.security.JWTAuthorizationTokenFilter;
import com.pitangproject.security.JWTLoginFilter;
import com.pitangproject.security.encoder.HashEncoder;
import com.pitangproject.security.service.JWTUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private JWTAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JWTUserDetailsService jwtUserDetailsService;

    // Custom JWT based security filter
    @Autowired
    JWTAuthorizationTokenFilter authenticationTokenFilter;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.route.authentication.path}")
    private String authenticationPath;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(jwtUserDetailsService)
            .passwordEncoder(passwordEncoderBean());
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
		
		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		
		.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/signup").permitAll()
		.antMatchers(HttpMethod.GET, "/signin").permitAll()
		.antMatchers(HttpMethod.POST, "/signup").permitAll()
		.antMatchers(HttpMethod.POST, "/signin").permitAll()
		.antMatchers("/h2-console/**/**").permitAll()
		.antMatchers("/auth/**").permitAll()
		.anyRequest().authenticated();

		http
        .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
		
		http
        .headers()
        .frameOptions().sameOrigin()  
        .cacheControl();
		/*
		 * .addFilterBefore(new JWTLoginFilter("/signin", authenticationManager()),
		 * UsernamePasswordAuthenticationFilter.class)
		 * 
		 * .addFilterBefore(new JWTAuthenticationFilter(),
		 * UsernamePasswordAuthenticationFilter.class);
		 */
	}

	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception {
	 * auth.inMemoryAuthentication().withUser("admin").password("password").roles(
	 * "ADMIN"); }
	 */
	
	 @Override
	    public void configure(WebSecurity web) throws Exception {
	        // AuthenticationTokenFilter will ignore the below paths
	        web
	            .ignoring()
	            .antMatchers(
	                HttpMethod.POST,
	                authenticationPath
	            )

	            // allow anonymous resource requests
	            .and()
	            .ignoring()
	            .antMatchers(
	                HttpMethod.GET,
	                "/",
	                "/*.html",
	                "/favicon.ico",
	                "/**/*.html",
	                "/**/*.css",
	                "/**/*.js"
	            )

	            // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
	            .and()
	            .ignoring()
	            .antMatchers("/h2-console/**/**");
	    }
}
