package com.devalexanderdaza.hulkstore.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devalexanderdaza.hulkstore.domain.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	public Optional<User> findByUsername(String username);

}
