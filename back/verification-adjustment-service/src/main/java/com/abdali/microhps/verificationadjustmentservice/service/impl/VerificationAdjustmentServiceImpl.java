package com.abdali.microhps.verificationadjustmentservice.service.impl;

import static com.abdali.microhps.verificationadjustmentservice.utils.Constants.REMOVAL_INDICATOR;
import static com.abdali.microhps.verificationadjustmentservice.utils.Constants.TOPIC_REMOVAL_NAME;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.verificationadjustmentservice.model.CoreTransactionModel;
import com.abdali.microhps.verificationadjustmentservice.model.RemovalDropTransaction;
import com.abdali.microhps.verificationadjustmentservice.model.RemovalTransaction;
import com.abdali.microhps.verificationadjustmentservice.producer.TransactionProducer;
import com.abdali.microhps.verificationadjustmentservice.proxy.DropTransactionProxy;
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
	DropTransactionProxy dropTransactionProxy;
	TransactionProducer transactionProducer;
    ObjectMapper objectMapper; 
	
	@Autowired
	public VerificationAdjustmentServiceImpl( 
			RemovalTransactionProxy removalTransactionProxy,
			DropTransactionProxy dropTransactionProxy,
			VerificationTransactionProxy verificationTransactionProxy, 
			TransactionProducer transactionProducer,
		    ObjectMapper objectMapper
			) { 
		this.removalTransactionProxy = removalTransactionProxy;
		this.dropTransactionProxy = dropTransactionProxy;
		this.verificationTransactionProxy = verificationTransactionProxy; 
		this.transactionProducer = transactionProducer;
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
			
			if(removalMessage != null) {
				// start verification processing -- using SUM DROPS.
				
			} else {
				// generate removal message based on verification transaction. i will added to topic directly.
				/**
				 * we need to get date for this removal message.
				 * PROCESS:
				 * get date for last removal message.
				 * find second drop message with sequence number equal 1 between removal date and verification date.
				 */
				Instant lastRemovalDate = removalTransactionProxy.getDatefromLastRemoval(deviceNumber, bagNumber);  
				
				Instant firstDropTransactionDate = dropTransactionProxy.getDatefromFirstDrop(deviceNumber, bagNumber, formatter.format(lastRemovalDate), formatter.format(currentVerificationTransactionDate));
				
				// generate removal transaction.
				RemovalTransaction generatedRemovalTransaction = objectMapper.readValue(consumerRecord.value(), RemovalTransaction.class);
				
				generatedRemovalTransaction.setTransmitionDate(firstDropTransactionDate.minus(1, ChronoUnit.SECONDS));
				generatedRemovalTransaction.setIndicator(REMOVAL_INDICATOR);
				//add drop attribute into it.
				RemovalDropTransaction removalDropTransaction = new RemovalDropTransaction();
				removalDropTransaction.setDepositReference("automatic");
				removalDropTransaction.setSequenceNumber(1);
				
				generatedRemovalTransaction.setRemovalDropTransaction(removalDropTransaction);
				
				// add to removal topic to save into db.
				transactionProducer.sendTransactionEvent(generatedRemovalTransaction, TOPIC_REMOVAL_NAME);
			}
		} 
	}
}
