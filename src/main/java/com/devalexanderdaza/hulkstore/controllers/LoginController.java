package com.devalexanderdaza.hulkstore.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devalexanderdaza.hulkstore.application.dto.UserDto;
import com.devalexanderdaza.hulkstore.application.service.FindUserByUsernameService;
import com.devalexanderdaza.hulkstore.controllers.request.LoginHttpRequest;
import com.devalexanderdaza.hulkstore.controllers.response.LoginResponse;
import com.devalexanderdaza.hulkstore.util.JwtProvider;

@RestController
public class LoginController {

	private AuthenticationManager authenticationManager;
	
	private FindUserByUsernameService service;
	
	private JwtProvider jwtProvider;
	
	public LoginController(
			AuthenticationManager authenticationManager, 
			FindUserByUsernameService service,
			JwtProvider jwtProvider) {
		this.authenticationManager = authenticationManager;
		this.service = service;
		this.jwtProvider = jwtProvider;
	}
	
	@PostMapping("/api/auth/login")
	public ResponseEntity<LoginResponse> handle(@RequestBody LoginHttpRequest request) {
	
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
		
		authenticationManager.authenticate(token);
		
		UserDto user = service.execute(request.getUsername());
		
		String jwtToken = jwtProvider.generateJwt(user);
		
		LoginResponse responseBody = LoginResponse.of(jwtToken);
		
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
			
	}
	
}
