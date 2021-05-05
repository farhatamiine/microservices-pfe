package com.abdali.microhps.validationexceptionservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdali.microhps.validationexceptionservice.validation.DeviceNumberExist;
import com.abdali.microhps.validationexceptionservice.validation.SequenceNumberDuplicated;
import com.abdali.microhps.validationexceptionservice.validation.TransactionIdDuplicated;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/exception-validation")
@Slf4j
public class ValidationExceptioinController {
	
	DeviceNumberExist deviceNumberExist;
	SequenceNumberDuplicated sequenceNumberDuplicated;
	TransactionIdDuplicated transactionIdDuplicated;
	
	@Autowired
	public ValidationExceptioinController(
			DeviceNumberExist deviceNumberExist,
			SequenceNumberDuplicated sequenceNumberDuplicated,
			TransactionIdDuplicated transactionIdDuplicated
			) {
		this.deviceNumberExist = deviceNumberExist;
		this.sequenceNumberDuplicated = sequenceNumberDuplicated;
		this.transactionIdDuplicated = transactionIdDuplicated;
	}


	@PostMapping("/indicator/{indicator}/sequenceNumber/{sequenceNumber}/bag/{bagNumber}/tranasction/{transactionId}/device/{deviceNumber}")
	public Boolean isPowerCardConditionValid(
			@PathVariable("indicator") char indicator,
			@PathVariable("sequenceNumber") int sequenceNumber, 
			@PathVariable("bagNumber") String bagNumber, 
			@PathVariable("transactionId") String transactionId,
			@PathVariable("deviceNumber") String deviceNumber) throws Exception { 
//		return transactionIdDuplicated.isTransactionIdDuplicated(indicator, transactionId);
		if(deviceNumberExist.isDeviceNumberExist(deviceNumber) && transactionIdDuplicated.isTransactionIdDuplicated(indicator, transactionId)) {
			return true;
		}
		return false;
	};
	
	// TODO:: Container Check - coins and Notes.
}
