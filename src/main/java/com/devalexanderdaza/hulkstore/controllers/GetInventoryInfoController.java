package com.devalexanderdaza.hulkstore.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devalexanderdaza.hulkstore.application.dto.InventoryInfoDto;
import com.devalexanderdaza.hulkstore.application.request.FilterOptions;
import com.devalexanderdaza.hulkstore.application.service.GetInventoryInfoService;
import com.devalexanderdaza.hulkstore.controllers.response.GetInventoryInfoResponse;

@RestController

public class GetInventoryInfoController {

	private GetInventoryInfoService service;

	public GetInventoryInfoController(GetInventoryInfoService service) {
		this.service = service;
	}
	
	@GetMapping("/api/inventory-info")
	public ResponseEntity<GetInventoryInfoResponse> handleRequest(
		@RequestParam(required = false) String franchiseId,
		@RequestParam(required = false) String categoryId
	) {
		
		FilterOptions filter = new FilterOptions();
		filter.setFranchiseId(franchiseId);
		filter.setCategoryId(categoryId);
		
		List<InventoryInfoDto> allData = service.execute(filter);
		
		GetInventoryInfoResponse responseBody = GetInventoryInfoResponse.of(allData);
		
		return ResponseEntity.ok(responseBody);
		
	}
	
}
