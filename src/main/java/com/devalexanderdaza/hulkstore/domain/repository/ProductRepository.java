package com.devalexanderdaza.hulkstore.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devalexanderdaza.hulkstore.domain.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
	
	public Optional<Product> findByCode(String code);

}
