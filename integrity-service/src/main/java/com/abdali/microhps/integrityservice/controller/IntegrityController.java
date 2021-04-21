package com.abdali.microhps.integrityservice.controller;

import static com.abdali.microhps.integrityservice.utils.Constants.DROP_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.MERCHANT_NUMBER_LENGTH;
import static com.abdali.microhps.integrityservice.utils.Constants.REMOVAL_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.VERIFICATION_INDICATOR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abdali.microhps.integrityservice.exceptions.NoDataFoundException;
import com.abdali.microhps.integrityservice.model.ResponseRequest;
import com.abdali.microhps.integrityservice.model.TransactionRequest;
import com.abdali.microhps.integrityservice.proxy.DropMessageProxy;
import com.abdali.microhps.integrityservice.proxy.RemovalMessageProxy;
import com.abdali.microhps.integrityservice.proxy.VerificationMessageProxy;
import com.abdali.microhps.integrityservice.service.TransactionService;
import com.abdali.microhps.integrityservice.validation.DuplicatedMessage;
import com.abdali.microhps.integrityservice.validation.MerchantNotFound;
import com.abdali.microhps.integrityservice.validation.MerchantNotMapped;
import com.abdali.microhps.integrityservice.validation.MessageFormat;
import com.abdali.microhps.integrityservice.validation.TransactionVerify;

import org.springframework.util.StringUtils;
 

@RestController
public class IntegrityController {

	private MessageFormat messageFormat;
	private MerchantNotFound merchantNotFound;
	private MerchantNotMapped merchantNotMapped;
	private DuplicatedMessage duplicatedMessage;
	private TransactionVerify transactionVerify;
	private DropMessageProxy dropMessageProxy;
	private RemovalMessageProxy removalMessageProxy;
	private VerificationMessageProxy verificationMessageProxy;
	
	@Autowired
	public IntegrityController(
			MessageFormat messageFormat,
			MerchantNotFound merchantNotFound,
			MerchantNotMapped merchantNotMapped,
			TransactionVerify transactionVerify,
			DropMessageProxy dropMessageProxy,
			DuplicatedMessage duplicatedMessage,
			RemovalMessageProxy removalMessageProxy,
			VerificationMessageProxy verificationMessageProxy
			) {
		// TODO Auto-generated constructor stub
		this.messageFormat = messageFormat;
		this.merchantNotFound = merchantNotFound;
		this.merchantNotMapped = merchantNotMapped;
		this.duplicatedMessage = duplicatedMessage;
		this.transactionVerify = transactionVerify;
		this.dropMessageProxy = dropMessageProxy;
		this.removalMessageProxy = removalMessageProxy;
		this.verificationMessageProxy = verificationMessageProxy;
	}
	
	
	@PostMapping("/check")
	public ResponseEntity<ResponseRequest> testController(@RequestBody TransactionRequest transactionRequest) {
		
		// variable.
		Long merchantNumber; 
		char indicator; 
		String deviceNumber;
		String bagNumber; 
		char containerType; 
		Integer sequenceNumber;
		// keep them string for now.
		String transmitionDate; 
		String transactionId;
		
		// split message into array
		String[] messageSplited = transactionRequest.getMessage().split(",");
		
		indicator = messageSplited[0].charAt(0);
		
		if(indicator == DROP_INDICATOR && messageSplited[1].length() == MERCHANT_NUMBER_LENGTH && messageSplited[1].matches("[0-9_]+")) {  
			
			merchantNumber = Long.parseLong(messageSplited[1]); 
			deviceNumber = messageSplited[2];
			bagNumber = messageSplited[3]; 
			containerType = messageSplited[4].charAt(0); 
			transactionId = messageSplited[6];
			sequenceNumber = Integer.parseInt(messageSplited[5]);
			transmitionDate = messageSplited[8];
			
			// GLOBAL STEPS 1 : INVALID MESSAGE FORMAT .
			if(messageFormat.checkMessageFormat(indicator, merchantNumber, deviceNumber, bagNumber, containerType, sequenceNumber, transmitionDate, transactionId) && duplicatedMessage.checkForDuplicatedMessage(merchantNumber, bagNumber, transmitionDate, Integer.parseInt(transactionId)) && merchantNotFound.checkMerchantNotFound(merchantNumber) && merchantNotMapped.simpleMerchant(merchantNumber, deviceNumber)) {
				// GLOBAL STEPS 2 & 3 & 4 : MERCHANT NOT FOUND - NOT MAPPED - TRANSACTION VERIFY.
				// check for message code status -- DOC: TransactionControl_V1 PAGE 8/38.
				String messageCodeStatus = messageSplited[(messageSplited.length - 1)];
				if(StringUtils.hasLength(messageCodeStatus) && messageCodeStatus.contentEquals("000")) {						
					dropMessageProxy.saveDropMessage(transactionRequest.getMessage());
				} else {
					/// send to others table.
				}
				// return message
				return requestResponse("process completed" + messageCodeStatus, HttpStatus.OK);
			} 
			
		} else if(indicator == REMOVAL_INDICATOR) { 
			
			merchantNumber = null; 
			deviceNumber = messageSplited[1];
			bagNumber = messageSplited[2]; 
			containerType = messageSplited[3].charAt(0); 
			transactionId = messageSplited[5];
			sequenceNumber = Integer.parseInt(messageSplited[4]);
			transmitionDate = messageSplited[7];
			
			// MERCHANT NOT EXIST FOR REMOVAL.
			if(messageFormat.checkMessageFormat(indicator, merchantNumber, deviceNumber, bagNumber, containerType, sequenceNumber, transmitionDate, transactionId)) {
				// GLOBAL STEPS 4 : TRANSACTION VERIFY.
				if(transactionVerify.transactionVerification(bagNumber)) {
					removalMessageProxy.saveRemovalMessage(transactionRequest.getMessage());
					return requestResponse("process completed", HttpStatus.OK);
				}
			}
		} else if(indicator == VERIFICATION_INDICATOR) {  
			
			merchantNumber = null; 
			deviceNumber = messageSplited[1];
			bagNumber = messageSplited[2]; 
			containerType = messageSplited[3].charAt(0); 
			transactionId = messageSplited[4];
			sequenceNumber = 0;
			transmitionDate = messageSplited[5];
			
			// MERCHANT AND SEQUENCE NOT EXIST FOR VERIFICATION.
			if(messageFormat.checkMessageFormat(indicator, merchantNumber, deviceNumber, bagNumber, containerType, sequenceNumber, transmitionDate, transactionId)) {
				// GLOBAL STEPS 4 : TRANSACTION VERIFY.
				if(transactionVerify.transactionVerification(bagNumber)) {
					verificationMessageProxy.saveVerificationMessage(transactionRequest.getMessage());
					return requestResponse("process completed", HttpStatus.OK);
				}
			}
		} else {		
			// TODO :: there is other INDICATORS to handle.
			throw new NoDataFoundException("the Indicator is not valid from Controller " + messageSplited[0].charAt(0) + " " + DROP_INDICATOR);
		}
		return requestResponse("process not completed", HttpStatus.CONFLICT);
	}
	
	
	private ResponseEntity<ResponseRequest> requestResponse(String message, HttpStatus httpStatus) {
		ResponseRequest responseRequest = new ResponseRequest();
		responseRequest.setMessage(message);
		responseRequest.setCode(httpStatus.value());
		return new ResponseEntity<ResponseRequest>(responseRequest, HttpStatus.OK);
	}
	
	
}
