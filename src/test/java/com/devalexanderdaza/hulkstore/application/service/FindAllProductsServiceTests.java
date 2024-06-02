package com.devalexanderdaza.hulkstore.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devalexanderdaza.hulkstore.application.data.CategoryData;
import com.devalexanderdaza.hulkstore.application.data.FranchiseData;
import com.devalexanderdaza.hulkstore.application.data.ProductData;
import com.devalexanderdaza.hulkstore.application.dto.ProductDto;
import com.devalexanderdaza.hulkstore.application.request.FilterOptions;
import com.devalexanderdaza.hulkstore.domain.repository.CategoryRepository;
import com.devalexanderdaza.hulkstore.domain.repository.FranchiseRepository;
import com.devalexanderdaza.hulkstore.domain.repository.ProductRepository;
import com.devalexanderdaza.hulkstore.domain.service.CategoryService;
import com.devalexanderdaza.hulkstore.domain.service.FranchiseService;
import com.devalexanderdaza.hulkstore.domain.service.ProductService;

/**
 * Use case: It gets a list of all the products stored in the database
 * @author Cristian
 *
 */

@ExtendWith(MockitoExtension.class)

final class FindAllProductsServiceTests {

	@Mock
	private FranchiseRepository franchiseRepository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@Mock
	private ProductRepository productRepository;
	
	private ProductService productService;
	
	private CategoryService categoryService;
	
	private FranchiseService franchiseService;
	
	private FindAllProductsService service;
	
	@BeforeEach
	public void setUp() {
		productService = new ProductService(productRepository);
		categoryService = new CategoryService(categoryRepository);
		franchiseService = new FranchiseService(franchiseRepository);
		service = new FindAllProductsService(productService, categoryService, franchiseService);
	}
	
	@Test
	@DisplayName("It should get an empty list of products")
	void shouldGetAnEmptyListOfProducts() {
		
		Mockito.when(productRepository.findAll()).thenReturn(Collections.emptyList());
		
		List<ProductDto> allProducts = service.execute(noFilter());
		
		assertNotNull(allProducts);
		assertEquals(0, allProducts.size());
		
	}
	
	@Test
	@DisplayName("It should get an non-empty list of products")
	void shouldGetAnNonEmptyListOfProducts() {
		
		Mockito.when(categoryRepository.findAll()).thenReturn(CategoryData.allCategories());
		Mockito.when(franchiseRepository.findAll()).thenReturn(FranchiseData.allFranchises());
		Mockito.when(productRepository.findAll()).thenReturn(ProductData.allProducts());
		
		List<ProductDto> allProducts = service.execute(noFilter());
		
		assertNotNull(allProducts);
		assertEquals(2, allProducts.size());
		
	}
	
	private FilterOptions noFilter() {
		return new FilterOptions();
	}
	
}
