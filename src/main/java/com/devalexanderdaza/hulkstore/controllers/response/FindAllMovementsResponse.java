package com.devalexanderdaza.hulkstore.controllers.response;

import java.util.List;

import com.devalexanderdaza.hulkstore.application.dto.MovementDto;

public class FindAllMovementsResponse {
	
	private List<MovementDto> movements;

	public FindAllMovementsResponse(List<MovementDto> movements) {
		this.movements = movements;
	}
	
	public static FindAllMovementsResponse of(List<MovementDto> movements) {
		return new FindAllMovementsResponse(movements);
	}

	public List<MovementDto> getMovements() {
		return movements;
	}

}
