package com.devalexanderdaza.hulkstore.application.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devalexanderdaza.hulkstore.application.dto.CategoryDto;
import com.devalexanderdaza.hulkstore.domain.service.CategoryService;

/**
 * It gets all the categories, sorted by name
 * @author Cristian
 *
 */

@Service

public class FindAllCategoriesService {

	private CategoryService categoryService;

	public FindAllCategoriesService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	public List<CategoryDto> execute() {
		
		return categoryService.findAllCategories()
							  .stream()
							  .map(CategoryDto::fromModel)
							  .sorted(Comparator.comparing(CategoryDto::getName))
							  .collect(Collectors.toList());
		
	}
	
}
