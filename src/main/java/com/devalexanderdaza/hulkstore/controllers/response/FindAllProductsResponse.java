package com.devalexanderdaza.hulkstore.controllers.response;

import java.util.List;

import com.devalexanderdaza.hulkstore.application.dto.ProductDto;

public class FindAllProductsResponse {
	
	private List<ProductDto> products;

	private FindAllProductsResponse(List<ProductDto> products) {
		this.products = products;
	}
	
	public static FindAllProductsResponse of(List<ProductDto> products) {
		return new FindAllProductsResponse(products);
	}

	public List<ProductDto> getProducts() {
		return products;
	}

}
