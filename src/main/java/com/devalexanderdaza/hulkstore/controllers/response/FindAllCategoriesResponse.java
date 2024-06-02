package com.devalexanderdaza.hulkstore.controllers.response;

import java.util.List;

import com.devalexanderdaza.hulkstore.application.dto.CategoryDto;

public class FindAllCategoriesResponse {
	
	private List<CategoryDto> categories;

	public FindAllCategoriesResponse(List<CategoryDto> categories) {
		super();
		this.categories = categories;
	}
	
	public static FindAllCategoriesResponse of(List<CategoryDto> categories) {
		return new FindAllCategoriesResponse(categories);
	}

	public List<CategoryDto> getCategories() {
		return categories;
	}

}
