package com.abdali.microhps.verificationservice.exceptions.types;

import java.util.List;

import lombok.Getter;

public class InvalidEntityException extends RuntimeException {
	
	@Getter
	private List<String> errors;
	
	public InvalidEntityException(String message, List<String> errors) {
		super(message);
		this.errors = errors;
	}
	
}
