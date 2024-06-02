package com.devalexanderdaza.hulkstore.application.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devalexanderdaza.hulkstore.application.data.RegisterIncomingInventoryRequestData;
import com.devalexanderdaza.hulkstore.application.data.ProductData;
import com.devalexanderdaza.hulkstore.application.request.RegisterIncomingInventoryRequest;
import com.devalexanderdaza.hulkstore.domain.repository.MovementRepository;
import com.devalexanderdaza.hulkstore.domain.repository.ProductRepository;
import com.devalexanderdaza.hulkstore.domain.service.MovementService;
import com.devalexanderdaza.hulkstore.domain.service.ProductService;
import com.devalexanderdaza.hulkstore.domain.service.exceptions.ProductDoesNotExistException;
import com.devalexanderdaza.hulkstore.domain.validator.InvalidFieldException;

@ExtendWith(MockitoExtension.class)

final class RegisterIncomingInventoryServiceTests {

	@Mock
	private MovementRepository movementRepository;
	
	@Mock
	private ProductRepository productRepository;
	
	private MovementService movementService;
	
	private ProductService productService;
	
	private RegisterIncomingInventoryService service;
	
	@BeforeEach
	public void setUp() {
		movementService = new MovementService(movementRepository);
		productService = new ProductService(productRepository);
		service = new RegisterIncomingInventoryService(movementService, productService);
	}
	
	@Test
	@DisplayName("Cannot allow to invoke service with a null parameter")
	void cannotInvokeServiceWithNullParameter() {
		
		assertThrows(InvalidFieldException.class, () -> service.execute(null) );
		
	}
	
	@Test
	@DisplayName("Cannot create a movement with null or empty fields.")
	void cannotCreateAMovementWithEmptyFields() {
		
		assertThrows(InvalidFieldException.class, () -> new RegisterIncomingInventoryRequest(null, null, null, null, null) );
		
	}
	
	@Test
	@DisplayName("Cannot create a movement if the Product Id is unknown")
	void cannotCreateMovementForAnUnknwonProduct() {
		
		RegisterIncomingInventoryRequest request = RegisterIncomingInventoryRequestData.incomingRequest();
		
		Mockito.when(productRepository.findById(request.getProductId())).thenReturn(Optional.empty());
		
		assertThrows(ProductDoesNotExistException.class, () -> service.execute(request) );
		
	}
	
	@Test
	@DisplayName("Cannot create a movement if the quantity is zero or below")
	void cannotCreateMovementWithQuantityZero() {
		
		RegisterIncomingInventoryRequest request = RegisterIncomingInventoryRequestData.withQuantityZero();
		
		assertThrows(InvalidFieldException.class, () -> service.execute(request));
		
	}
	
	@Test
	@DisplayName("should create a valid incoming inventory movement")
	void createAValidIncomingInventoryMovement() {

		RegisterIncomingInventoryRequest request = RegisterIncomingInventoryRequestData.incomingRequest();
		
		Mockito.when(productRepository.findById(request.getProductId())).thenReturn(Optional.of(ProductData.product1()));
		
		assertDoesNotThrow( () -> service.execute(request) );
		
	}
	
	
}
