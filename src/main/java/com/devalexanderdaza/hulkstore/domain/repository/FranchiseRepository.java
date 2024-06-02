package com.devalexanderdaza.hulkstore.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devalexanderdaza.hulkstore.domain.model.Franchise;

public interface FranchiseRepository extends JpaRepository<Franchise, String> {

}
