package com.devalexanderdaza.hulkstore.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devalexanderdaza.hulkstore.application.dto.MovementDto;
import com.devalexanderdaza.hulkstore.application.service.FindAllMovementsService;
import com.devalexanderdaza.hulkstore.controllers.response.FindAllMovementsResponse;

@RestController

public class FindAllMovementsController {

	private FindAllMovementsService service;
	
	public FindAllMovementsController(FindAllMovementsService service) {
		this.service = service;
	}

	@GetMapping("/api/movements")
	public ResponseEntity<FindAllMovementsResponse> handleRequest(
		@RequestParam(required = false) String productId
	) {
		
		List<MovementDto> allMovements = service.execute(productId);
		
		FindAllMovementsResponse responseBody = FindAllMovementsResponse.of(allMovements);
		
		return ResponseEntity.ok(responseBody);
		
	}
	
}
