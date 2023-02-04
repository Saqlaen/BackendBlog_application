package com.api.blogginApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.blogginApplication.exceptions.AuthException;
import com.api.blogginApplication.payloads.JwtAuthenticationRequest;
import com.api.blogginApplication.payloads.JwtAuthenticationResponse;
import com.api.blogginApplication.security.JwtTokenHelper;

@RestController
@RequestMapping("/api/v1/auth")
public class JwtAuthController {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	

	@PostMapping("/login")
	public ResponseEntity<?> createToken( @RequestBody JwtAuthenticationRequest jwtAuthRequest ){
		
		JwtAuthenticationResponse response = new JwtAuthenticationResponse();

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
				new UsernamePasswordAuthenticationToken( jwtAuthRequest.getUsername(),
														 jwtAuthRequest.getPassword() );
		
		Authentication authentication =  authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		
		if( authentication.isAuthenticated() ) {
			
			response.setToken(jwtTokenHelper.generateTokenForUser( jwtAuthRequest.getUsername() )  );
			
		}
		else {
			throw new UsernameNotFoundException("invalid username or password ");
		}
		
		return new ResponseEntity< JwtAuthenticationResponse >( response, HttpStatus.OK );
	
	}
	
	
}
