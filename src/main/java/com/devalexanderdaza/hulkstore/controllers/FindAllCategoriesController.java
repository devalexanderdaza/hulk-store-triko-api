package com.devalexanderdaza.hulkstore.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devalexanderdaza.hulkstore.application.dto.CategoryDto;
import com.devalexanderdaza.hulkstore.application.service.FindAllCategoriesService;
import com.devalexanderdaza.hulkstore.controllers.response.FindAllCategoriesResponse;

@RestController

public class FindAllCategoriesController {

	private FindAllCategoriesService service;
	
	public FindAllCategoriesController(FindAllCategoriesService service) {
		this.service = service;
	}

	@GetMapping("/api/categories")
	public ResponseEntity<FindAllCategoriesResponse> handleRequest() {
		
		List<CategoryDto> allCategories = service.execute();
		
		FindAllCategoriesResponse responseBody = FindAllCategoriesResponse.of(allCategories);
		
		return ResponseEntity.ok(responseBody);
		
	}
	
}
