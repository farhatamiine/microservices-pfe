package com.abdali.microhps.integrityservice.validation.powerCard;
import static com.abdali.microhps.integrityservice.utils.Constants.POWERCARD_VALIDATION_CODE;
import static com.abdali.microhps.integrityservice.utils.Constants.POWERCARD_VALIDATION_DESCRIPTION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.integrityservice.exceptions.IntegrityException;
import com.abdali.microhps.integrityservice.model.Transaction;
import com.abdali.microhps.integrityservice.proxy.ValidationExceptionProxy;
import com.abdali.microhps.integrityservice.service.TransactionBuilder;
import com.abdali.microhps.integrityservice.service.TransactionService;

@Service
public class PowerCardValidation {
	
	private ValidationExceptionProxy validationExceptionProxy;
	private TransactionService transactionService;
	private TransactionBuilder transactionBuilder;
	
	@Autowired
	public PowerCardValidation(
			ValidationExceptionProxy validationExceptionProxy,
			TransactionService transactionService,
			TransactionBuilder transactionBuilder
			) {
		this.validationExceptionProxy = validationExceptionProxy;
		this.transactionService = transactionService;
		this.transactionBuilder = transactionBuilder;
	}
	
	
	public Boolean powerCardSimpleValidation(int sequenceNumber, String bagNumber, String transactionId, String deviceNumber, char containerType, String[] messageSplited, String message) {
		
		if(validationExceptionProxy.isPowerCardConditionValid(sequenceNumber, bagNumber, transactionId, deviceNumber)) {
			return true;
		}
		Transaction transaction = transactionBuilder.transactionBuild(containerType, messageSplited, message);
		transactionService.transactionCreate(transaction);
		throw new IntegrityException(POWERCARD_VALIDATION_CODE, POWERCARD_VALIDATION_DESCRIPTION);
	}
}
