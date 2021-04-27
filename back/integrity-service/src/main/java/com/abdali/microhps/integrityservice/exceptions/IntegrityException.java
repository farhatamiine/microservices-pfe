package com.abdali.microhps.integrityservice.exceptions;

import lombok.Getter;

public class IntegrityException extends RuntimeException {
	
	@Getter
	private String errorCode;
	@Getter
	private String code;
	
	public IntegrityException(String code, String message) {
		super(message);
		this.code = code;
		this.errorCode = errorCode;
	}
}
