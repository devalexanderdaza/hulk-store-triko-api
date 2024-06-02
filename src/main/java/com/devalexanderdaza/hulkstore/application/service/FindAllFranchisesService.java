package com.devalexanderdaza.hulkstore.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devalexanderdaza.hulkstore.application.dto.FranchiseDto;
import com.devalexanderdaza.hulkstore.domain.service.FranchiseService;

/**
 * Use case: find all franchises, like Marvel, DC and others.
 * @author Cristian
 *
 */

@Service

public class FindAllFranchisesService {
	
	private FranchiseService franchiseService;
	
	public FindAllFranchisesService(FranchiseService franchiseService) {
		this.franchiseService = franchiseService;
	}

	public List<FranchiseDto> execute() {
		
		return franchiseService.findAllFranchises()
		                       .stream()
		                       .map(FranchiseDto::fromModel)
		                       .collect(Collectors.toList());
		
	}

}
