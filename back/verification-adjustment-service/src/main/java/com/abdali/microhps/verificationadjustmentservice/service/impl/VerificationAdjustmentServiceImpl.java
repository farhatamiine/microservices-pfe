package com.abdali.microhps.verificationadjustmentservice.service.impl;

import static com.abdali.microhps.verificationadjustmentservice.utils.Constants.REMOVAL_INDICATOR;
import static com.abdali.microhps.verificationadjustmentservice.utils.Constants.TOPIC_REMOVAL_NAME;
import static com.abdali.microhps.verificationadjustmentservice.utils.Constants.VERIFICATION_SETTLEMENT_MODE;
import static com.abdali.microhps.verificationadjustmentservice.utils.Constants.CREDITED_TYPE;
import static com.abdali.microhps.verificationadjustmentservice.utils.Constants.PRODUCER_TOPIC_PRE_CLEARED;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.abdali.microhps.verificationadjustmentservice.model.AccountLimitsModel;
import com.abdali.microhps.verificationadjustmentservice.model.CoreTransactionModel;
import com.abdali.microhps.verificationadjustmentservice.model.RemovalDropTransaction;
import com.abdali.microhps.verificationadjustmentservice.model.RemovalTransaction;
import com.abdali.microhps.verificationadjustmentservice.producer.PreClearedTransactionProducer;
import com.abdali.microhps.verificationadjustmentservice.producer.TransactionProducer;
import com.abdali.microhps.verificationadjustmentservice.proxy.DropTransactionProxy;
import com.abdali.microhps.verificationadjustmentservice.proxy.MerchantDeviceProxy;
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
	MerchantDeviceProxy merchantDeviceProxy;
	TransactionProducer transactionProducer;
	PreClearedTransactionProducer preClearedTransactionProducer;
    ObjectMapper objectMapper; 
	
	@Autowired
	public VerificationAdjustmentServiceImpl( 
			RemovalTransactionProxy removalTransactionProxy,
			DropTransactionProxy dropTransactionProxy,
			VerificationTransactionProxy verificationTransactionProxy, 
			TransactionProducer transactionProducer,
			MerchantDeviceProxy merchantDeviceProxy,
			PreClearedTransactionProducer preClearedTransactionProducer,
		    ObjectMapper objectMapper
			) { 
		this.removalTransactionProxy = removalTransactionProxy;
		this.dropTransactionProxy = dropTransactionProxy;
		this.verificationTransactionProxy = verificationTransactionProxy; 
		this.transactionProducer = transactionProducer;
		this.merchantDeviceProxy = merchantDeviceProxy;
		this.preClearedTransactionProducer = preClearedTransactionProducer;
	    this.objectMapper = objectMapper; 
	} 
	
	
	public void validateTransaction(ConsumerRecord<Long,String> consumerRecord) throws JsonMappingException, JsonProcessingException { 
		
		CoreTransactionModel verificationMessage = objectMapper.readValue(consumerRecord.value(), CoreTransactionModel.class);
		String deviceNumber = verificationMessage.getDeviceNumber();
		String bagNumber = verificationMessage.getBagNumber();
		Integer transactionId = verificationMessage.getTransactionId();
		
		// add verification value to pre cleared.
		if(verificationMessage.getMerchantSettlementMode() == VERIFICATION_SETTLEMENT_MODE) { 
			verificationMessage.setTypeCD(CREDITED_TYPE);
			preClearedTransactionProducer.sendTransactionEvent(verificationMessage, PRODUCER_TOPIC_PRE_CLEARED);
		}
				
				
		CoreTransactionModel lastVerificationMessage = verificationTransactionProxy.findVerificationTransaction(deviceNumber, bagNumber, transactionId);
		
		if(lastVerificationMessage != null) { 
			
			Instant lastVerificationTransactionDate = lastVerificationMessage.getTransmitionDate();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");  
			Instant currentVerificationTransactionDate = verificationMessage.getTransmitionDate();	
			 
			/************** START :: check if REMOVAL message EXIST **************************/
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
			/************** END :: check if REMOVAL message EXIST **************************/
			
			/************** START :: VERIFICATION Process **************************/
			/*
			 * get drops between the two removal.
			 * get merchant min/max exchanges.
			 */
			Long merchantNumber = dropTransactionProxy.getMerchantNumber(deviceNumber, bagNumber);
			
			AccountLimitsModel accountLimits = merchantDeviceProxy.getMerchantLimits(merchantNumber);
			
			/************** END :: VERIFICATION Process **************************/
		} 
	}
}
