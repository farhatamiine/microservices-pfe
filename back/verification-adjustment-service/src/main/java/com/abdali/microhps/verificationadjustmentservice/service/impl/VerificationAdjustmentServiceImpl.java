package com.abdali.microhps.verificationadjustmentservice.service.impl;
 
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.verificationadjustmentservice.model.CoreTransactionModel;
import com.abdali.microhps.verificationadjustmentservice.proxy.RemovalTransactionProxy;
import com.abdali.microhps.verificationadjustmentservice.proxy.VerificationTransactionProxy;
import com.abdali.microhps.verificationadjustmentservice.service.VerificationAdjustmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/** 
 * @author samurai.
 * 1 - after get verification message : find removal message for it.
 * 	   if not create removal message for it using verification transaction.
 * 		- to find it search between two verification message with same devicenUmber and bagNumber.
 */
@Service
@Slf4j
public class VerificationAdjustmentServiceImpl implements VerificationAdjustmentService {
	 
	RemovalTransactionProxy removalTransactionProxy; 
	VerificationTransactionProxy verificationTransactionProxy;
    ObjectMapper objectMapper; 
	
	@Autowired
	public VerificationAdjustmentServiceImpl( 
			RemovalTransactionProxy removalTransactionProxy,
			VerificationTransactionProxy verificationTransactionProxy, 
		    ObjectMapper objectMapper
			) { 
		this.removalTransactionProxy = removalTransactionProxy;
		this.verificationTransactionProxy = verificationTransactionProxy; 
	    this.objectMapper = objectMapper; 
	} 
	
	
	public void validateTransaction(ConsumerRecord<Long,String> consumerRecord) throws JsonMappingException, JsonProcessingException { 
		
		CoreTransactionModel verificationMessage = objectMapper.readValue(consumerRecord.value(), CoreTransactionModel.class);
		String deviceNumber = verificationMessage.getDeviceNumber();
		String bagNumber = verificationMessage.getBagNumber();
		Integer transactionId = verificationMessage.getTransactionId();
		
		CoreTransactionModel lastVerificationMessage = verificationTransactionProxy.findVerificationTransaction(deviceNumber, bagNumber, transactionId);
		
		if(lastVerificationMessage != null) { 
			
			Instant lastVerificationTransactionDate = lastVerificationMessage.getTransmitionDate();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");  
			Instant currentVerificationTransactionDate = verificationMessage.getTransmitionDate();	
			 
			 // find removal message.
			CoreTransactionModel removalMessage = removalTransactionProxy.removalMessageBetwwenDates(deviceNumber, bagNumber, formatter.format(lastVerificationTransactionDate), formatter.format(currentVerificationTransactionDate));
			
		} 
	}
}
