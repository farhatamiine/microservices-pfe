package com.abdali.microhps.integrityservice.controller;

import static com.abdali.microhps.integrityservice.utils.Constants.DROP_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.MERCHANT_NUMBER_LENGTH;
import static com.abdali.microhps.integrityservice.utils.Constants.REMOVAL_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.VERIFICATION_INDICATOR;

import static com.abdali.microhps.integrityservice.utils.Constants.MESSAGE_INVALID_CODE;
import static com.abdali.microhps.integrityservice.utils.Constants.MESSAGE_INVALID_DESCRIPTION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abdali.microhps.integrityservice.exceptions.IntegrityException;
import com.abdali.microhps.integrityservice.exceptions.NoDataFoundException; 
import com.abdali.microhps.integrityservice.proxy.DropMessageProxy;
import com.abdali.microhps.integrityservice.service.TransactionService;
import com.abdali.microhps.integrityservice.validation.MerchantNotFound;
import com.abdali.microhps.integrityservice.validation.MerchantNotMapped;
import com.abdali.microhps.integrityservice.validation.MessageFormat;
import com.abdali.microhps.integrityservice.validation.TransactionVerify;
 

@RestController
public class IntegrityController {

	private MessageFormat messageFormat;
	private MerchantNotFound merchantNotFound;
	private TransactionService transactionService;
	private DropMessageProxy dropMessageProxy;
	private MerchantNotMapped merchantNotMapped;
	private TransactionVerify transactionVerify;
	
	@Autowired
	public IntegrityController(
			MessageFormat messageFormat,
			MerchantNotFound merchantNotFound,
			TransactionService transactionService,
			DropMessageProxy dropMessageProxy,
			MerchantNotMapped merchantNotMapped,
			TransactionVerify transactionVerify
			) {
		// TODO Auto-generated constructor stub
		this.messageFormat = messageFormat;
		this.merchantNotFound = merchantNotFound;
		this.transactionService = transactionService;
		this.dropMessageProxy = dropMessageProxy;
		this.merchantNotMapped = merchantNotMapped;
		this.transactionVerify = transactionVerify;
	}
	
	
	@PostMapping("/check")
	public Boolean testController(@RequestParam("message") String message) {
		
		Boolean message_format = false;
		
		// variable 
		Long merchantNumber; 
		char indicator; 
		String deviceNumber;
		String bagNumber; 
		char containerType; 
		Integer sequenceNumber;
		// we keep them string for now
		String transmitionDate; 
		String transactionId;
		
		
		// split message into array
		String[] data = message.split(",");
		
		indicator = data[0].charAt(0); 
		
		// -- Check for drop message and verify Merchant Number -- contain just numbers with 15 in length.
		if(indicator == DROP_INDICATOR && data[1].matches("[0-9]+") && data[1].length() == MERCHANT_NUMBER_LENGTH) {  
			
			merchantNumber = Long.parseLong(data[1]); 
			deviceNumber = data[2];
			bagNumber = data[3]; 
			containerType = data[4].charAt(0); 
			transactionId = data[6];
			sequenceNumber = Integer.parseInt(data[5]);
			transmitionDate = data[8];
			
			// GLOBAL STEPS 1 : INVALID MESSAGE FORMAT .
			if(messageFormat.checkMessageFormat(indicator, merchantNumber, deviceNumber, bagNumber, containerType, sequenceNumber, transmitionDate, transactionId)) {
				// GLOBAL STEPS 2 & 3 & 4 : MERCHANT NOT FOUND - NOT MAPPED - TRANSACTION VERIFY.
				if(merchantNotFound.checkMerchantNotFound(merchantNumber) && merchantNotMapped.simpleMerchant(merchantNumber, deviceNumber) && transactionVerify.transactionVerification(bagNumber)) {
					// return message
					return true;
				}
			}
			
		} else if(indicator == REMOVAL_INDICATOR) { 
			merchantNumber = null; 
			deviceNumber = data[1];
			bagNumber = data[2]; 
			containerType = data[3].charAt(0); 
			transactionId = data[5];
			sequenceNumber = Integer.parseInt(data[4]);
			transmitionDate = data[7];
			// don't have just merchant
			message_format = messageFormat.checkMessageFormat(indicator, merchantNumber, deviceNumber, bagNumber, containerType, sequenceNumber, transmitionDate, transactionId);
		} else if(indicator == VERIFICATION_INDICATOR) {  
			
			merchantNumber = null; 
			deviceNumber = data[1];
			bagNumber = data[2]; 
			containerType = data[3].charAt(0); 
			transactionId = data[4];
			sequenceNumber = 0;
			transmitionDate = data[5];
			// don't have merchantNumber and sequence
			message_format = messageFormat.checkMessageFormat(indicator, merchantNumber, deviceNumber, bagNumber, containerType, sequenceNumber, transmitionDate, transactionId);
		} else {			
			throw new NoDataFoundException("the Indicator is not valid from Controller " + data[0].charAt(0) + " " + DROP_INDICATOR);
		}
		return message_format;
	}
	
}
