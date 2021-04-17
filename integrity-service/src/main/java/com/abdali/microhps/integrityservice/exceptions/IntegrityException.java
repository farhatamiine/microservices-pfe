package com.abdali.microhps.integrityservice.exceptions;

import lombok.Getter;

public class IntegrityException extends RuntimeException {
	
	@Getter
	private String errorCode;
	
	public IntegrityException(String code, String message) {
		super(message);
		this.errorCode = errorCode;
	}
}
