package com.abdali.microhps.integrityservice.exceptions.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString 
public class ErrorMessage {
	
	private int status;
	
	private String message;

	private List<String> errors;
	
}
