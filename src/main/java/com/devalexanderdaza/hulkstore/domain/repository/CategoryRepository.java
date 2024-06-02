package com.devalexanderdaza.hulkstore.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devalexanderdaza.hulkstore.domain.model.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {

}
