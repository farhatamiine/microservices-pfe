package com.abdali.microhps.dropservice.exceptions.types;

import java.util.List;

import lombok.Getter;

public class InvalidValidationException extends RuntimeException {
	
	@Getter
	private List<String> errors;
	
	public InvalidValidationException(String message, List<String> errors) {
		super(message);
		this.errors = errors;
	}
	
}
