package com.devalexanderdaza.hulkstore.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devalexanderdaza.hulkstore.domain.model.User;
import com.devalexanderdaza.hulkstore.domain.service.UserService;
import com.devalexanderdaza.hulkstore.domain.service.exceptions.UserDoesNotExistException;

@Service

public class CustomUserDetailsService implements UserDetailsService {

	private UserService userService;
	
	public CustomUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {

		User user = null;
		
		try {
			user = userService.findByUsername(username);
		} catch(UserDoesNotExistException exp) {
			throw new UsernameNotFoundException(username);
		}
		
		return new CustomUserDetails(user);
		
	}

}
