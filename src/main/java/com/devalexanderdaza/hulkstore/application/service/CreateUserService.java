package com.devalexanderdaza.hulkstore.application.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.devalexanderdaza.hulkstore.application.request.CreateUserRequest;
import com.devalexanderdaza.hulkstore.domain.model.User;
import com.devalexanderdaza.hulkstore.domain.service.UserService;
import com.devalexanderdaza.hulkstore.domain.validator.FieldValidator;

@Service

public class CreateUserService {
	
	private UserService userService;

	public CreateUserService(UserService userService) {
		this.userService = userService;
	}

	public void execute(CreateUserRequest request) {
		
		FieldValidator.notNull(request, "CreateUserRequest");
		
		userService.checkIfUsernameIsAvailable(request.getUsername());
		
		String encodedPassword = new BCryptPasswordEncoder().encode(request.getPassword());
		
		User user = User.of(
				request.getId(), 
				request.getUsername(), 
				encodedPassword, 
				request.getFirstName(), 
				request.getLastName()
		);
		
		userService.save(user);
		
	}

}
