package com.devalexanderdaza.hulkstore.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devalexanderdaza.hulkstore.domain.model.Movement;
import com.devalexanderdaza.hulkstore.domain.repository.MovementRepository;

@Service

public class MovementService {

	private MovementRepository repository;

	public MovementService(MovementRepository repository) {
		this.repository = repository;
	}

	public void saveMovement(Movement movement) {
		repository.save(movement);
	}
	
	public List<Movement> findAllMovementsByProduct(String productId) {
		return repository.findByProductId(productId);
	}
	
	public List<Movement> findAllMovements() {
		return repository.findAll();
	}
	
}
