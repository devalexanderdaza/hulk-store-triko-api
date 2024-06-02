package com.devalexanderdaza.hulkstore.controllers.response;

public class CreateUserResponse {

	private String id;

	public CreateUserResponse(String id) {
		this.id = id;
	}
	
	public static CreateUserResponse of(String id) {
		return new CreateUserResponse(id);
	}

	public String getId() {
		return id;
	}

}
