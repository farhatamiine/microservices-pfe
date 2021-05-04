package com.abdali.microhps.integrityservice.controller;

import static com.abdali.microhps.integrityservice.utils.Constants.DROP_INDICATOR;
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
import com.abdali.microhps.integrityservice.model.Transaction;
import com.abdali.microhps.integrityservice.model.TransactionRequest;
import com.abdali.microhps.integrityservice.producer.TransactionProducer;
import com.abdali.microhps.integrityservice.service.TransactionBuilder;
import com.abdali.microhps.integrityservice.validation.DuplicatedMessage;
import com.abdali.microhps.integrityservice.validation.MerchantNotFound;
import com.abdali.microhps.integrityservice.validation.MerchantNotMapped;
import com.abdali.microhps.integrityservice.validation.MessageFormat;
import com.abdali.microhps.integrityservice.validation.TransactionVerify;
import com.abdali.microhps.integrityservice.validation.powerCard.PowerCardValidation;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.util.StringUtils;
 

@RestController
public class IntegrityController {

	private MessageFormat messageFormat;
	private MerchantNotFound merchantNotFound;
	private MerchantNotMapped merchantNotMapped;
	private DuplicatedMessage duplicatedMessage;
	private TransactionVerify transactionVerify;
	private TransactionBuilder transactionBuilder;
	private TransactionProducer transactionProducer;
	private PowerCardValidation powerCardValidation;
	
	@Autowired
	public IntegrityController(
			MessageFormat messageFormat,
			MerchantNotFound merchantNotFound,
			MerchantNotMapped merchantNotMapped,
			TransactionVerify transactionVerify,
			DuplicatedMessage duplicatedMessage,
			TransactionBuilder transactionBuilder,
			TransactionProducer transactionProducer,
			PowerCardValidation powerCardValidation
			) {
		// TODO Auto-generated constructor stub
		this.messageFormat = messageFormat;
		this.merchantNotFound = merchantNotFound;
		this.merchantNotMapped = merchantNotMapped;
		this.duplicatedMessage = duplicatedMessage;
		this.transactionVerify = transactionVerify;
		this.transactionBuilder = transactionBuilder;
		this.transactionProducer = transactionProducer;
		this.powerCardValidation = powerCardValidation;
	}
	
	
	@PostMapping("/check")
	public ResponseEntity<ResponseRequest> checkMessage(@RequestBody TransactionRequest transactionRequest) throws JsonProcessingException {
		
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

		String messageCodeStatus = messageSplited[(messageSplited.length - 1)];
		
		if(indicator == DROP_INDICATOR) {  
			
			merchantNumber = Long.parseLong(messageSplited[1]); 
			deviceNumber = messageSplited[2];
			bagNumber = messageSplited[3]; 
			containerType = messageSplited[4].charAt(0); 
			transactionId = messageSplited[6];
			sequenceNumber = Integer.parseInt(messageSplited[5]);
			transmitionDate = messageSplited[8];
			
			
			// GLOBAL STEPS 1 : INVALID MESSAGE FORMAT .
			if(messageFormat.checkMessageFormat(indicator, messageSplited[1], deviceNumber, bagNumber, containerType, sequenceNumber, transmitionDate, transactionId, messageSplited, transactionRequest.getMessage()) 
					&& duplicatedMessage.checkForDuplicatedMessage(merchantNumber, bagNumber, transmitionDate, Integer.parseInt(transactionId), containerType, messageSplited, transactionRequest.getMessage()) 
					&& merchantNotFound.checkMerchantNotFound(merchantNumber, containerType, messageSplited, transactionRequest.getMessage()) 
					&& merchantNotMapped.simpleMerchant(merchantNumber, deviceNumber, containerType, messageSplited, transactionRequest.getMessage())
					&& powerCardValidation.powerCardSimpleValidation(sequenceNumber, bagNumber, transactionId, deviceNumber, containerType, messageSplited, transactionRequest.getMessage())) {				
					// GLOBAL STEPS 2 & 3 & 4 : MERCHANT NOT FOUND - NOT MAPPED - TRANSACTION VERIFY.
					// check for message code status -- DOC: TransactionControl_V1 PAGE 8/38. 
					if(StringUtils.hasLength(messageCodeStatus) && messageCodeStatus.contentEquals("000")) {						
						// dropMessageProxy.saveDropMessage(transactionRequest.getMessage()); // old approach save into DB using micro-proxy-service.
						Transaction transaction = transactionBuilder.transactionBuild(containerType, messageSplited, transactionRequest.getMessage());
						String topic = "drop-transaction-events";
						transactionProducer.sendTransactionEvent(transaction, topic);
					} else {
						// save Into OthersTable.
						throw new NoDataFoundException("need to be saved into SMB_OTHERS_TABLE : not available for now");
					}
					// return message
					return requestResponse("process completed", HttpStatus.OK);
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
			if(messageFormat.checkMessageFormat(indicator, null, deviceNumber, bagNumber, containerType, sequenceNumber, transmitionDate, transactionId, messageSplited, transactionRequest.getMessage())
					&& transactionVerify.transactionVerification(bagNumber, containerType, messageSplited, transactionRequest.getMessage())) {

				if(StringUtils.hasLength(messageCodeStatus) && messageCodeStatus.contentEquals("000")) {	
					Transaction transaction = transactionBuilder.transactionBuild(containerType, messageSplited, transactionRequest.getMessage());
					String topic = "removal-transaction-events";
					transactionProducer.sendTransactionEvent(transaction, topic);
				} else {
					// save Into OthersTable.
					throw new NoDataFoundException("need to be saved into SMB_OTHERS_TABLE : not available for now");
				}
				
				return requestResponse("process completed", HttpStatus.OK);
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
			if(messageFormat.checkMessageFormat(indicator, null, deviceNumber, bagNumber, containerType, sequenceNumber, transmitionDate, transactionId, messageSplited, transactionRequest.getMessage())
					&& transactionVerify.transactionVerification(bagNumber, containerType, messageSplited, transactionRequest.getMessage())) {

				if(StringUtils.hasLength(messageCodeStatus) && messageCodeStatus.contentEquals("000")) {	
					Transaction transaction = transactionBuilder.transactionBuild(containerType, messageSplited, transactionRequest.getMessage());
					String topic = "verification-transaction-events";
					transactionProducer.sendTransactionEvent(transaction, topic);
				} else {
					// save Into OthersTable.
					throw new NoDataFoundException("need to be saved into SMB_OTHERS_TABLE : not available for now");
				}
				
				return requestResponse("process completed", HttpStatus.OK);
			}
		} else {
			/*
			 * more check here to do 
			 * if i didn't know the indicator i can't know the format
			 */
			// TODO :: there is other INDICATORS to handle.
			throw new NoDataFoundException("UNKNOW Indicator Another Type of message or invlaid");
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
