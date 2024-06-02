package com.devalexanderdaza.hulkstore.application.service;

import org.springframework.stereotype.Service;

import com.devalexanderdaza.hulkstore.application.dto.UserDto;
import com.devalexanderdaza.hulkstore.domain.model.User;
import com.devalexanderdaza.hulkstore.domain.service.UserService;

@Service

public class FindUserByUsernameService {

	private UserService userService;

	public FindUserByUsernameService(UserService userService) {
		this.userService = userService;
	}

	public UserDto execute(String username) {
		
		User user = userService.findByUsername(username);
		
		return UserDto.fromModel(user);
	}
	
}
