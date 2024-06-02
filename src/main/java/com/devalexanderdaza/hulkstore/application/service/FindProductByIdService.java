package com.devalexanderdaza.hulkstore.application.service;

import org.springframework.stereotype.Service;

import com.devalexanderdaza.hulkstore.application.dto.ProductDto;
import com.devalexanderdaza.hulkstore.domain.model.Category;
import com.devalexanderdaza.hulkstore.domain.model.Franchise;
import com.devalexanderdaza.hulkstore.domain.model.Product;
import com.devalexanderdaza.hulkstore.domain.service.CategoryService;
import com.devalexanderdaza.hulkstore.domain.service.FranchiseService;
import com.devalexanderdaza.hulkstore.domain.service.ProductService;
import com.devalexanderdaza.hulkstore.domain.validator.FieldValidator;

@Service

public class FindProductByIdService {

	private ProductService productService;
	
	private CategoryService categoryService;
	
	private FranchiseService franchiseService;

	public FindProductByIdService(ProductService productService, CategoryService categoryService,
			FranchiseService franchiseService) {
		this.productService = productService;
		this.categoryService = categoryService;
		this.franchiseService = franchiseService;
	}
	
	public ProductDto execute(String productId) {
		
		FieldValidator.notNull(productId, "productId");
		
		Product product = productService.findById(productId);
		
		Category category = categoryService.findById(product.getCategoryId());
		
		Franchise franchise = franchiseService.findById(product.getFranchiseId());
		
		return ProductDto.from(product, category, franchise);
		
	}
	
}
