package com.devalexanderdaza.hulkstore.domain.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devalexanderdaza.hulkstore.domain.model.User;
import com.devalexanderdaza.hulkstore.domain.repository.UserRepository;
import com.devalexanderdaza.hulkstore.domain.service.exceptions.UserDoesNotExistException;
import com.devalexanderdaza.hulkstore.domain.service.exceptions.UsernameAlreadyExistsException;

@Service

public class UserService {
	
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User findByUsername(String username) {
		
		Optional<User> optional = userRepository.findByUsername(username);
		
		if(!optional.isPresent()) {
			throw new UserDoesNotExistException(username);
		}
		
		return optional.get();
		
	}
	
	public void save(User user) {
		userRepository.save(user);
	}
	
	public void checkIfUsernameIsAvailable(String username) {
		
		Optional<User> optional = userRepository.findByUsername(username);
		
		if(optional.isPresent()) {
			throw new UsernameAlreadyExistsException(username);
		}
		
	}

}
