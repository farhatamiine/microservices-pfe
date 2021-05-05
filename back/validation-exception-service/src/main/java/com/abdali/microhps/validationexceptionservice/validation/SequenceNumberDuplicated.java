package com.abdali.microhps.validationexceptionservice.validation;

import org.springframework.stereotype.Service;

@Service
public class SequenceNumberDuplicated {
	
	public Boolean isSequenceNumberDuplicated(int SequenceNumber, String bagNumber, String deviceNumber) {
		// check if is duplicated for same bag number and device in the same day.
		return true;
	}
}
