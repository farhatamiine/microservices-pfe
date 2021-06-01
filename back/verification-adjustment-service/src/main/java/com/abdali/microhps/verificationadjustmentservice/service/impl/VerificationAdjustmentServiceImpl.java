package com.abdali.microhps.verificationadjustmentservice.service.impl;

import static com.abdali.microhps.verificationadjustmentservice.utils.Constants.REMOVAL_INDICATOR;
import static com.abdali.microhps.verificationadjustmentservice.utils.Constants.TOPIC_REMOVAL_NAME;
import static com.abdali.microhps.verificationadjustmentservice.utils.Constants.VERIFICATION_SETTLEMENT_MODE;
import static com.abdali.microhps.verificationadjustmentservice.utils.Constants.CREDITED_TYPE;
import static com.abdali.microhps.verificationadjustmentservice.utils.Constants.DROP_SETTLEMENT_MODE;
import static com.abdali.microhps.verificationadjustmentservice.utils.Constants.PRODUCER_TOPIC_PRE_CLEARED;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
 
import com.abdali.microhps.verificationadjustmentservice.model.AccountLimitsModel;
import com.abdali.microhps.verificationadjustmentservice.model.AdjustmentEvent;
import com.abdali.microhps.verificationadjustmentservice.model.CoreTransactionModel;
import com.abdali.microhps.verificationadjustmentservice.model.RemovalDropTransaction;
import com.abdali.microhps.verificationadjustmentservice.model.RemovalTransaction; 
import com.abdali.microhps.verificationadjustmentservice.producer.PreClearedTransactionProducer;
import com.abdali.microhps.verificationadjustmentservice.producer.TransactionProducer;
import com.abdali.microhps.verificationadjustmentservice.proxy.AdjustmentEventProxy;
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
 * @author Samurai.
 * 1 - after get verification message : find removal message for it.
 * 	   if not create removal message for it using verification transaction.
 * 		- to find it search between two verification message with same devicenUmber and bagNumber.
 */
@Service 
public class VerificationAdjustmentServiceImpl implements VerificationAdjustmentService {
	 
	RemovalTransactionProxy removalTransactionProxy; 
	VerificationTransactionProxy verificationTransactionProxy;
	DropTransactionProxy dropTransactionProxy;
	MerchantDeviceProxy merchantDeviceProxy;
	AdjustmentEventProxy adjustmentEventProxy;
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
			AdjustmentEventProxy adjustmentEventProxy,
			PreClearedTransactionProducer preClearedTransactionProducer,
		    ObjectMapper objectMapper
			) { 
		this.removalTransactionProxy = removalTransactionProxy;
		this.dropTransactionProxy = dropTransactionProxy;
		this.verificationTransactionProxy = verificationTransactionProxy; 
		this.transactionProducer = transactionProducer;
		this.merchantDeviceProxy = merchantDeviceProxy;
		this.adjustmentEventProxy = adjustmentEventProxy;
		this.preClearedTransactionProducer = preClearedTransactionProducer;
	    this.objectMapper = objectMapper; 
	} 
	
	
	public void validateTransaction(ConsumerRecord<Long,String> consumerRecord) throws JsonMappingException, JsonProcessingException { 
		
		
		CoreTransactionModel verificationMessage = objectMapper.readValue(consumerRecord.value(), CoreTransactionModel.class);
		String deviceNumber = verificationMessage.getDeviceNumber();
		String bagNumber = verificationMessage.getBagNumber();
		Integer transactionId = verificationMessage.getTransactionId();
		
		// add verification value to pre-cleared.
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
			 * get merchant min/max exchanges.
			 */
			Long merchantNumber = dropTransactionProxy.getMerchantNumber(deviceNumber, bagNumber);
			
			AccountLimitsModel accountLimits = merchantDeviceProxy.getMerchantLimits(merchantNumber);
			
			Double minValue = accountLimits.getMinExchange();
			Double maxValue = accountLimits.getMaxExchange();
			
			/*
			 * This validation fails when the verification message value does not agree to the sum of drops received (including the adjustment if any).
			 * get SUMDROPS to compare with verification value;
			 * Transaction Management Document from Page 20/27.
			 */
			
			/*** Drops ***/
			List<CoreTransactionModel> listDrops = dropTransactionProxy.listDropsBetwwenDates(deviceNumber, bagNumber, formatter.format(lastVerificationTransactionDate), formatter.format(currentVerificationTransactionDate));
			BigDecimal sumDrops = new BigDecimal(0);
			for (CoreTransactionModel dropTransaction : listDrops) { 
				sumDrops = sumDrops.add(dropTransaction.getTotalAmount());  
			}
			
			/*** Adjustment from removal -- amount -- credited(normal) -- debited ***/
			AdjustmentEvent removalAdjustment = adjustmentEventProxy.getAdjustment(merchantNumber, formatter.format(lastVerificationTransactionDate));
			
			removalAdjustment.getTransferSign();
			
			sumDrops = sumDrops.add(removalAdjustment.getTransferAmount());
			
			/*
			 * 1 if a is greater than b
			 * -1 if b is less than b
			 * 0 if a is equal to b
			 */
			if(verificationMessage.getTotalAmount().compareTo(sumDrops) == 1) {
				

				String caseMessage = null;
				
				/**
				 * Document : TRX-Details :: PAGE --> 20/27.
				 *  When the verification message is greater than the sum of drop we are talking about a surplus in that case the merchant has been short credited.
				 */
				BigDecimal diffrentBetween = verificationMessage.getTotalAmount().subtract(sumDrops);
				
				if(diffrentBetween.compareTo(new BigDecimal(maxValue)) == 1) {
					/*  X > Max vendor value.
						Create case
						- No automatic processing is done.
						- Case with the credit transaction as follow.
						- Create a credit adjustment transaction to the customer DDA account.
						- User must approve the case to trigger the processing of the adjustment to DDA account after investigation
					 */
					
				} else if(diffrentBetween.compareTo(new BigDecimal(maxValue)) == -1 && diffrentBetween.compareTo(new BigDecimal(minValue)) == 1) {
					/*  min value < X < max value.
						Credit customer DDA:
						- Create a case with the credit transaction as follow.
						- Create a credit adjustment transaction to the customer DDA account and process it automatically.
					 */
				} else {
					/*  X <= min vendor value.
						Credit Vendor DDA:
					   - Retrieve the account of the vendor.
					   - Create a case with the credit transaction as follow.
					   - Create a credit adjustment transaction to the vendor DDA account and process it automatically.
					 */
				}
				
				// FOR Adjustement Event
//				removalEvents.setTransferAmount(verificationMessage.getTotalAmount().subtract(sumDrops));
//				removalEvents.setTransferSign(TransferSign.C);
//				String accountNumber = merchantDeviceProxy.getMerchantAccount(verificationMessage.getMerchantNumber(), CREDITED_TYPE);
//				removalEvents.setAccountNumber(accountNumber);
				caseMessage = "sum of the drops is smaller than the removal value. Means the customer has been short credited."; 
				
				// if the mode is on drop and the sum drops is less than the removal we need to credited merchant account.
				if(verificationMessage.getMerchantSettlementMode() == DROP_SETTLEMENT_MODE) { 
					// TODO :: add into Pre-cleared topic.
					CoreTransactionModel transactionPreCleared = new CoreTransactionModel();
					transactionPreCleared.setBagNumber(bagNumber);
					transactionPreCleared.setDeviceNumber(deviceNumber);
					transactionPreCleared.setTransactionId(transactionId);
					transactionPreCleared.setMerchantNumber(verificationMessage.getMerchantNumber());
//					transactionPreCleared.setTransmitionDate(currentRemovalTransactionDate);
					transactionPreCleared.setCurrency(verificationMessage.getCurrency());
					transactionPreCleared.setTotalAmount(verificationMessage.getTotalAmount().subtract(sumDrops));
					transactionPreCleared.setTypeCD(CREDITED_TYPE);
					preClearedTransactionProducer.sendTransactionEvent(transactionPreCleared, PRODUCER_TOPIC_PRE_CLEARED);
				}
				
				
			} else if (verificationMessage.getTotalAmount().compareTo(sumDrops) == -1) {
				 
			}
			/************** END :: VERIFICATION Process **************************/
		} 
	}
}
