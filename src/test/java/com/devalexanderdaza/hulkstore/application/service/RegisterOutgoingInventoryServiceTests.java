package com.devalexanderdaza.hulkstore.application.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devalexanderdaza.hulkstore.application.data.MovementData;
import com.devalexanderdaza.hulkstore.application.data.ProductData;
import com.devalexanderdaza.hulkstore.application.data.RegisterOutgoingInventoryRequestData;
import com.devalexanderdaza.hulkstore.application.request.RegisterOutgoingInventoryRequest;
import com.devalexanderdaza.hulkstore.domain.repository.MovementRepository;
import com.devalexanderdaza.hulkstore.domain.repository.ProductRepository;
import com.devalexanderdaza.hulkstore.domain.service.MovementService;
import com.devalexanderdaza.hulkstore.domain.service.ProductService;
import com.devalexanderdaza.hulkstore.domain.service.exceptions.ProductDoesNotExistException;
import com.devalexanderdaza.hulkstore.domain.service.exceptions.ProductWithoutEnoughStockException;
import com.devalexanderdaza.hulkstore.domain.validator.InvalidFieldException;

@ExtendWith(MockitoExtension.class)

final class RegisterOutgoingInventoryServiceTests {

	@Mock
	private MovementRepository movementRepository;
	
	@Mock
	private ProductRepository productRepository;
	
	private MovementService movementService;
	
	private ProductService productService;
	
	private RegisterOutgoingInventoryService service;
	
	@BeforeEach
	public void setUp() {
		movementService = new MovementService(movementRepository);
		productService = new ProductService(productRepository);
		service = new RegisterOutgoingInventoryService(movementService, productService);
	}
	
	@Test
	@DisplayName("Cannot allow to invoke service with a null parameter")
	void cannotInvokeServiceWithNullParameter() {
		
		assertThrows(InvalidFieldException.class, () -> service.execute(RegisterOutgoingInventoryRequestData.nullRequest()) );
		assertThrows(InvalidFieldException.class, () -> RegisterOutgoingInventoryRequestData.wihEmptyFields() );
		
	}
	
	@Test
	@DisplayName("Cannot create the movement if the quantity is zero or below")
	void cannotCreateMovementWithQuantityZero() {

		RegisterOutgoingInventoryRequest request = RegisterOutgoingInventoryRequestData.withQuantityZero();
		
		assertThrows(InvalidFieldException.class, () -> service.execute(request));
		
	}
	
	@Test
	@DisplayName("Cannot create a movement if the Product Id is unknown")
	void cannotCreateMovementForAnUnknwonProduct() {
		
		RegisterOutgoingInventoryRequest request = RegisterOutgoingInventoryRequestData.outgoingRequest();
		
		Mockito.when(productRepository.findById(request.getProductId())).thenReturn(Optional.empty());
		
		assertThrows(ProductDoesNotExistException.class, () -> service.execute(request) );
		
	}

	@Test
	@DisplayName("Cannot create the movement if the product does not have any incoming movement first")
	void cannotCreateMovementIfHasNoIncomingMovements() {
		
		RegisterOutgoingInventoryRequest request = RegisterOutgoingInventoryRequestData.outgoingRequest();
		
		mockProduct(request.getProductId());
		
		Mockito.when(movementRepository.findByProductId(request.getProductId())).thenReturn(Collections.emptyList());

		assertThrows(ProductWithoutEnoughStockException.class, () -> service.execute(request));
		
	}
	
	@Test
	@DisplayName("Cannot create the movement if the product does not have enough stock")
	void cannotCreateMovementIfProductDoesntHaveEnoughStock() {
		
		String productId = "a5300e96-2968-467c-9f54-79eb0bedc94d";
		mockProduct(productId);
		mockMovements(productId);
		
		String movementId = "5d364b17-bf10-4b35-9aff-adfebf04b8eb";
		Integer quantity = 1000;
		BigDecimal unitPrice = new BigDecimal("5000");
		
		RegisterOutgoingInventoryRequest request = new RegisterOutgoingInventoryRequest(movementId, productId, quantity, unitPrice, "");
		
		assertThrows(ProductWithoutEnoughStockException.class, () -> service.execute(request));
		
	}
	
	@Test
	@DisplayName("Create a valid outgoing movement")
	void shouldCreateAValidOutgoingMovement() {

		RegisterOutgoingInventoryRequest request = RegisterOutgoingInventoryRequestData.outgoingRequest();
		mockProduct(request.getProductId());
		mockMovements(request.getProductId());
		
		assertDoesNotThrow(() -> service.execute(request));
		
	}
	
	private void mockProduct(String productId) {
		Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(ProductData.product1()));
	}
	
	private void mockMovements(String productId) {
		Mockito.when(movementRepository.findByProductId(productId)).thenReturn(MovementData.allMovements());
		
	}
	
}
