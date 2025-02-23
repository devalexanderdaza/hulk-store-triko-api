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

import com.devalexanderdaza.hulkstore.application.data.FranchiseData;
import com.devalexanderdaza.hulkstore.application.dto.FranchiseDto;
import com.devalexanderdaza.hulkstore.domain.model.Franchise;
import com.devalexanderdaza.hulkstore.domain.repository.FranchiseRepository;
import com.devalexanderdaza.hulkstore.domain.service.FranchiseService;

/**
 * Use case: Get all the franchises. 
 * For example: Marvel, DC, etc.
 */

@ExtendWith(MockitoExtension.class)

final class FindAllFranchisesServiceTests {

	@Mock
	private FranchiseRepository franchiseRepository;
	
	private FranchiseService franchiseService;
	
	private FindAllFranchisesService service;
	
	@BeforeEach
	public void setUp() {
		franchiseService = new FranchiseService(franchiseRepository);
		service = new FindAllFranchisesService(franchiseService);
	}
	
	@Test
	@DisplayName("Should get a non empty list of franchises")
	void shouldGetANonEmptyList() {
		
		int expectedFranchises = 3;
		
		mockFranchisesInDBWith(FranchiseData.allFranchises());
		
		List<FranchiseDto> franchises = service.execute();
		
		assertNotNull(franchises);
		assertEquals(expectedFranchises, franchises.size());
		
	}
	
	
	@Test
	@DisplayName("Should get an empty list of franchises")
	void shouldGetAnEmptyList() {
		
		int expectedFranchises = 0;
		
		mockFranchisesInDBWith(Collections.emptyList());
		
		List<FranchiseDto> franchises = service.execute();
		
		assertNotNull(franchises);
		assertEquals(expectedFranchises, franchises.size());
		
	}
	
	private void mockFranchisesInDBWith(List<Franchise> mockList) {
		Mockito.when(franchiseRepository.findAll()).thenReturn(mockList);
	}
	
}
