package com.devalexanderdaza.hulkstore.application.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devalexanderdaza.hulkstore.application.dto.InventoryInfoDto;
import com.devalexanderdaza.hulkstore.application.request.FilterOptions;
import com.devalexanderdaza.hulkstore.domain.model.Movement;
import com.devalexanderdaza.hulkstore.domain.model.MovementType;
import com.devalexanderdaza.hulkstore.domain.model.Product;
import com.devalexanderdaza.hulkstore.domain.service.MovementService;
import com.devalexanderdaza.hulkstore.domain.service.ProductService;

@Service

public class GetInventoryInfoService {
	
	private MovementService movementService;
	private ProductService productService;

	public GetInventoryInfoService(MovementService movementService, ProductService productService) {
		this.movementService = movementService;
		this.productService = productService;
	}

	public List<InventoryInfoDto> execute(FilterOptions filter) {
		
		List<Product> allProducts = productService.findAllProducts(filter);
		
		if(allProducts.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<Movement> allMovements = movementService.findAllMovements();
		
		List<Movement> allIncomings = filterMovementsByType(allMovements, MovementType.INCOMINGS);
		
		List<Movement> allOutgoings = filterMovementsByType(allMovements, MovementType.OUTGOINGS);
		
		return allProducts.stream()
		           .map(product -> {
		        	   
		        	   String productId = product.getId();
		        	   
		        	   Integer totalIncomings = filterByProductAndGetTotalQuantity(allIncomings, productId);
		        	   
		        	   Integer totalOutgoings = filterByProductAndGetTotalQuantity(allOutgoings, productId);
		        	   
		        	   return InventoryInfoDto.of(productId, totalIncomings, totalOutgoings);
		        	   
		           })
		           .collect(Collectors.toList());

		
	}
	
	private List<Movement> filterMovementsByType(List<Movement> allMovements, MovementType type) {
		return allMovements.stream()
                           .filter(m -> m.getType().equals(type))
                           .collect(Collectors.toList());
	}
	
	private Integer filterByProductAndGetTotalQuantity( List<Movement> movements, String productId ) {
		return movements.stream()
                        .filter(m -> m.getProductId().equals(productId))
                        .collect(Collectors.summingInt(Movement::getQuantity));
	}

}
