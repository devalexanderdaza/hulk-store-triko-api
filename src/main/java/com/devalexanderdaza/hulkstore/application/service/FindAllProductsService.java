package com.devalexanderdaza.hulkstore.application.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devalexanderdaza.hulkstore.application.dto.ProductDto;
import com.devalexanderdaza.hulkstore.application.request.FilterOptions;
import com.devalexanderdaza.hulkstore.domain.model.Category;
import com.devalexanderdaza.hulkstore.domain.model.Franchise;
import com.devalexanderdaza.hulkstore.domain.model.Product;
import com.devalexanderdaza.hulkstore.domain.service.CategoryService;
import com.devalexanderdaza.hulkstore.domain.service.FranchiseService;
import com.devalexanderdaza.hulkstore.domain.service.ProductService;
import com.devalexanderdaza.hulkstore.domain.validator.FieldValidator;

@Service

public class FindAllProductsService {

	private ProductService productService;
	private CategoryService categoryService;
	private FranchiseService franchiseService;
	
	public FindAllProductsService(ProductService productService, CategoryService categoryService,
			FranchiseService franchiseService) {
		this.productService = productService;
		this.categoryService = categoryService;
		this.franchiseService = franchiseService;
	}
	
	public List<ProductDto> execute(FilterOptions filter) {
		
		FieldValidator.notNull(filter, "FindAllProductsFilter");
		
		List<Product> allProducts = productService.findAllProducts(filter);
		
		if(allProducts.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<Category> allCategories = categoryService.findAllCategories();
		List<Franchise> allFranchises = franchiseService.findAllFranchises();
		
		return allProducts.stream()
		           .map(product -> {
		        	   
		        	   Category category = allCategories.stream()
		        			                            .filter(c -> c.getId().equals(product.getCategoryId()))
		        			                            .findFirst()
		        			                            .get();
		        	   
		        	   Franchise franchise = allFranchises.stream()
		        			                              .filter(f -> f.getId().equals(product.getFranchiseId()))
		        			                              .findFirst()
		        			                              .get();
		        	   
		        	   return ProductDto.from(product, category, franchise);
		        	   
		           })
		           .sorted(Comparator.comparing(ProductDto::getCode))
		           .collect(Collectors.toList());
		
	}
	
}
