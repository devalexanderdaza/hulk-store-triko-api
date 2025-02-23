package com.devalexanderdaza.hulkstore.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devalexanderdaza.hulkstore.application.request.RegisterIncomingInventoryRequest;
import com.devalexanderdaza.hulkstore.application.request.RegisterOutgoingInventoryRequest;
import com.devalexanderdaza.hulkstore.domain.model.Movement;
import com.devalexanderdaza.hulkstore.domain.model.MovementType;
import com.devalexanderdaza.hulkstore.domain.service.MovementService;
import com.devalexanderdaza.hulkstore.domain.service.ProductService;
import com.devalexanderdaza.hulkstore.domain.service.exceptions.ProductWithoutEnoughStockException;
import com.devalexanderdaza.hulkstore.domain.validator.FieldValidator;

@Service

public class RegisterOutgoingInventoryService {

	private MovementService movementService;
	private ProductService productService;
	
	public RegisterOutgoingInventoryService(MovementService movementService, ProductService productService) {
		this.movementService = movementService;
		this.productService = productService;
	}
	
	public void execute(RegisterOutgoingInventoryRequest request) {
		
		checkNotNull(request);
		
		checkIfQuantityIsGreaterThanZero( request.getQuantity() );
		
		checkIfProductExists( request.getProductId() );
		
		checkIfProductHasAvailableStock( request.getProductId(), request.getQuantity() );
		
		Movement movement = convertToOutgoingMovement(request);
		
		movementService.saveMovement(movement);
		
	}

	private void checkIfProductExists(String productId) {
		productService.checkIfProductExists(productId);
	}
	
	private void checkNotNull(RegisterIncomingInventoryRequest request) {
		FieldValidator.notNull(request, "RegisterIncomingInventoryRequest");
	}
	
	private void checkIfQuantityIsGreaterThanZero(Integer quantity) {
		FieldValidator.notZeroOrNegative(quantity, "quantity");
	}
	
	private Movement convertToOutgoingMovement(RegisterIncomingInventoryRequest request) {

		return Movement.of(
				request.getId(), 
				request.getProductId(), 
				MovementType.OUTGOINGS, 
				request.getQuantity(), 
				request.getUnitPrice(), 
				request.getObservation(), 
				LocalDateTime.now()
		);
	}
	
	private void checkIfProductHasAvailableStock(String productId, Integer quantity) {
		
		List<Movement> allMovements = movementService.findAllMovementsByProduct(productId);
		
		if(allMovements.isEmpty()) {
			throw new ProductWithoutEnoughStockException(productId);
		}
		
		Integer incomings = allMovements.stream()
				                        .filter(movement -> movement.getType().equals(MovementType.INCOMINGS))
				                        .collect(Collectors.summingInt(Movement::getQuantity));
				                        
		Integer outgoings = allMovements.stream()
				                        .filter(movement -> movement.getType().equals(MovementType.OUTGOINGS))
				                        .collect(Collectors.summingInt(Movement::getQuantity));
		
		Integer availableStock = incomings - outgoings;
		
		if((availableStock - quantity) < 0) {
			throw new ProductWithoutEnoughStockException(productId);
		}
		
	}
	
}
