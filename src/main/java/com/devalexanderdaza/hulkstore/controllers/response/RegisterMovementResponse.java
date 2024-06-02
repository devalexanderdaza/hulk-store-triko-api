package com.devalexanderdaza.hulkstore.controllers.response;

public class RegisterMovementResponse {
	
	private String movementId;

	public RegisterMovementResponse(String movementId) {
		this.movementId = movementId;
	}
	
	public static RegisterMovementResponse of(String movementId) {
		return new RegisterMovementResponse(movementId);
	}

	public String getMovementId() {
		return movementId;
	}

}
