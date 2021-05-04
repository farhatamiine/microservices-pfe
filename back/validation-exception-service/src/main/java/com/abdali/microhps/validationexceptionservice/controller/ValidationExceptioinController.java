package com.abdali.microhps.validationexceptionservice.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception-validation")
public class ValidationExceptioinController {

	@PostMapping("/transactionId/{transactionId}")
	public Boolean isTransactionIdDuplicated(@PathVariable String TransactionId) {
		return true;
	}
	
	@PostMapping("/deviceNumber/{deviceNumber}")
	public Boolean isDeviceNumberExist(@PathVariable String deviceNumber) {
		return false;
	}
	
	@PostMapping("/deviceNumber/{deviceNumber}/bagNumber/{bagNumber}/sequenceNumber/{sequenceNumber}")
	public Boolean isSequenceNumberDuplicated(@PathVariable String SequenceNumber, @PathVariable String bagNumber, @PathVariable String deviceNumber) {
		// check if is duplicated for same bag number and device in the same day.
		return true;
	}
	
	// TODO:: Container Check - coins and Notes.
}
