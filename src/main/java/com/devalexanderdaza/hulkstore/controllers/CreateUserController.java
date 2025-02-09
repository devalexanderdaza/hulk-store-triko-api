package com.devalexanderdaza.hulkstore.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devalexanderdaza.hulkstore.application.request.CreateUserRequest;
import com.devalexanderdaza.hulkstore.application.service.CreateUserService;
import com.devalexanderdaza.hulkstore.controllers.request.CreateUserHttpRequest;
import com.devalexanderdaza.hulkstore.controllers.response.CreateUserResponse;

@RestController

public class CreateUserController {

	private CreateUserService service;

	public CreateUserController(CreateUserService service) {
		super();
		this.service = service;
	}

	@PostMapping("/api/auth/sign-up")
	public ResponseEntity<CreateUserResponse> handleRequest(@RequestBody CreateUserHttpRequest requestBody) {
		
		CreateUserRequest request = toRequest(requestBody);
		
		service.execute(request);
		
		CreateUserResponse responseBody = CreateUserResponse.of(request.getId());
		
		return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
		
	}
	
	private CreateUserRequest toRequest(CreateUserHttpRequest requestBody) {
		return CreateUserRequest.of(
				UUID.randomUUID().toString(), 
				requestBody.getUsername(), 
				requestBody.getPassword(), 
				requestBody.getFirstName(), 
				requestBody.getLastName()
		);
	}
	
}
