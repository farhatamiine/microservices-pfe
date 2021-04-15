package com.abdali.microhps.integrityservice.exceptions.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString 
public class IntegrityMessage {

	private String status;
	
	private String message;
}
