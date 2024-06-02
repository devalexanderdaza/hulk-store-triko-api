package com.devalexanderdaza.hulkstore.application.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

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
import com.devalexanderdaza.hulkstore.domain.model.Product;
import com.devalexanderdaza.hulkstore.domain.repository.CategoryRepository;
import com.devalexanderdaza.hulkstore.domain.repository.FranchiseRepository;
import com.devalexanderdaza.hulkstore.domain.repository.ProductRepository;
import com.devalexanderdaza.hulkstore.domain.service.CategoryService;
import com.devalexanderdaza.hulkstore.domain.service.FranchiseService;
import com.devalexanderdaza.hulkstore.domain.service.ProductService;
import com.devalexanderdaza.hulkstore.domain.service.exceptions.ProductDoesNotExistException;

@ExtendWith(MockitoExtension.class)

final class FindProductByIdServiceTests {
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@Mock
	private FranchiseRepository franchiseRepository;
	
	private ProductService productService;
	
	private CategoryService categoryService;
	
	private FranchiseService franchiseService;
	
	private FindProductByIdService service;
	
	
	@BeforeEach
	public void setUp() {
		productService = new ProductService(productRepository);
		categoryService = new CategoryService(categoryRepository);
		franchiseService = new FranchiseService(franchiseRepository);
		service = new FindProductByIdService(productService, categoryService, franchiseService);
	}

	@Test
	@DisplayName("should throw an exception because product id does not exist")
	void shouldNotFindAnyProduct() {
		
		assertThrows(ProductDoesNotExistException.class, () -> service.execute("unknown-product-id"));
		
	}
	
	@Test
	@DisplayName("Should find an existing product")
	void shouldFindAnExistingProduct() {

		Product product = ProductData.product1();
		
		Mockito.when( productRepository.findById(product.getId()) ).thenReturn( Optional.of(product) );
		
		Mockito.when(categoryRepository.findById(product.getCategoryId())).thenReturn( Optional.of(CategoryData.comics()) );
		
		Mockito.when(franchiseRepository.findById(product.getFranchiseId())).thenReturn( Optional.of(FranchiseData.marvelComics()) );
		
		ProductDto dto = service.execute(product.getId());
		
		assertNotNull(dto);
		
	}
	
}
