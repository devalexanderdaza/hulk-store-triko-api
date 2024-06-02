package com.devalexanderdaza.hulkstore.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devalexanderdaza.hulkstore.domain.model.Movement;

public interface MovementRepository extends JpaRepository<Movement, String> {
	
	public List<Movement> findByProductId(String id);

}
