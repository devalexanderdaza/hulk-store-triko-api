package com.devalexanderdaza.hulkstore.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devalexanderdaza.hulkstore.domain.model.Category;
import com.devalexanderdaza.hulkstore.domain.repository.CategoryRepository;
import com.devalexanderdaza.hulkstore.domain.service.exceptions.CategoryDoesNotExistException;

@Service

public class CategoryService {

	private CategoryRepository repository;

	public CategoryService(CategoryRepository repository) {
		this.repository = repository;
	}
	
	public List<Category> findAllCategories() {
		return repository.findAll();
	}
	
	public void checkIfCategoryExists(String categoryId) {
		Optional<Category> optional = repository.findById(categoryId);
		if(!optional.isPresent()) {
			throw new CategoryDoesNotExistException(categoryId);
		}
	}
	
	public Category findById(String id) {
		Optional<Category> optional = repository.findById(id);
		if(!optional.isPresent()) {
			throw new CategoryDoesNotExistException(id);
		}
		return optional.get();
	}
	
}
