package com.abdali.microhps.removaladjustmentservice.serivce.impl;

import static com.abdali.microhps.removaladjustmentservice.utils.Constants.PRODUCER_TOPIC_PRE_CLEARED;
import static com.abdali.microhps.removaladjustmentservice.utils.Constants.CREDITED_TYPE; 
import static com.abdali.microhps.removaladjustmentservice.utils.Constants.REMOVAL_SETTLEMENT_MODE;
import static com.abdali.microhps.removaladjustmentservice.utils.Constants.DROP_SETTLEMENT_MODE;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant; 
import java.util.List; 

import org.apache.commons.lang.RandomStringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.abdali.microhps.removaladjustmentservice.model.AdjustmentEvent;
import com.abdali.microhps.removaladjustmentservice.model.CaseInformataion;
import com.abdali.microhps.removaladjustmentservice.model.CoreTransactionModel;
import com.abdali.microhps.removaladjustmentservice.model.enumeration.TransferSign;
import com.abdali.microhps.removaladjustmentservice.producer.PreClearedTransaction;
import com.abdali.microhps.removaladjustmentservice.proxy.DropTransactionProxy;
import com.abdali.microhps.removaladjustmentservice.proxy.MerchantDeviceProxy;
import com.abdali.microhps.removaladjustmentservice.proxy.RemovalTransactionProxy;
import com.abdali.microhps.removaladjustmentservice.repository.AdjustmentEventRepository;
import com.abdali.microhps.removaladjustmentservice.repository.CaseInformationRepository;
import com.abdali.microhps.removaladjustmentservice.serivce.RemovalAdjustmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RemovalAdjustmentServiceImpl implements RemovalAdjustmentService {
	
	MerchantDeviceProxy merchantDeviceProxy;
	RemovalTransactionProxy removalTransactionProxy;
	DropTransactionProxy dropTransactionService;
	PreClearedTransaction preClearedTransaction;
    ObjectMapper objectMapper;
    AdjustmentEventRepository adjustementEventRepository;
    CaseInformationRepository caseInformationRepository;
	
	@Autowired
	public RemovalAdjustmentServiceImpl(
			MerchantDeviceProxy merchantDeviceProxy,
			RemovalTransactionProxy removalTransactionProxy,
			DropTransactionProxy dropTransactionProxy,
			PreClearedTransaction preClearedTransaction,
		    ObjectMapper objectMapper,
		    AdjustmentEventRepository adjustementEventRepository,
		    CaseInformationRepository caseInformationRepository
			) {
		this.merchantDeviceProxy = merchantDeviceProxy;
		this.adjustementEventRepository = adjustementEventRepository;
		this.removalTransactionProxy = removalTransactionProxy;
		this.dropTransactionService = dropTransactionProxy;
	    this.objectMapper = objectMapper;
	    this.preClearedTransaction = preClearedTransaction;
	    this.caseInformationRepository = caseInformationRepository;
	} 
	
	/*
	 * add list of drops to topic. will did in removal adjustment service.
	 * if mode is ON Drop and when we get Removal for this Drops we need to check if everything is balanced.
	 * STEPS: 1 - GET REMOVAL RECORD : DEVICE NUMBER - BAG NUMBER 
	 * 		  2 - get last removal message date from removal db.
	 * 		  3 - use DEVICE NUMBER - BAG NUMBER - DATETIME to get drop TRANSACTIONS.
	 * 		  4 - CAlculate SUM of DROPS and compare wit REMOVAL AMOUNT.
	 *        5 - If its OK Publish Event inside "KafkaTopic" Else save into Db event with simple explanation and publish into "KafkaTopic".
	 */
	public void validateTransaction(ConsumerRecord<Long,String> consumerRecord) throws JsonMappingException, JsonProcessingException { 
		
		CoreTransactionModel removalMessage = objectMapper.readValue(consumerRecord.value(), CoreTransactionModel.class);
		String deviceNumber = removalMessage.getDeviceNumber();
		String bagNumber = removalMessage.getBagNumber();
		Integer transactionId = removalMessage.getTransactionId();
		
		// add to drop adjustment topic. just if mode is removal or it will be duplicated.
		if(removalMessage.getMerchantSettlementMode() == REMOVAL_SETTLEMENT_MODE) { 
			removalMessage.setTypeCD(CREDITED_TYPE);
			preClearedTransaction.sendTransactionEvent(removalMessage, PRODUCER_TOPIC_PRE_CLEARED);
		}
		
		CoreTransactionModel lastRemovalMessage = removalTransactionProxy.findRemovalTransaction(deviceNumber, bagNumber, transactionId);
		
		if(lastRemovalMessage != null) {
			
			Instant lastRemovalTransactionDate = lastRemovalMessage.getTransmitionDate();
			Instant currentRemovalTransactionDate = removalMessage.getTransmitionDate();	
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");  
			
			List<CoreTransactionModel> listDrops = dropTransactionService.listDropsBetwwenDates(deviceNumber, bagNumber, formatter.format(lastRemovalTransactionDate), formatter.format(currentRemovalTransactionDate));
			BigDecimal sumDrops = new BigDecimal(0);
			for (CoreTransactionModel dropTransaction : listDrops) { 
				sumDrops = sumDrops.add(dropTransaction.getTotalAmount());  
			}
			if(removalMessage.getTotalAmount().equals(sumDrops) ) {
				
				log.info("removal Amount is equal sum(Drops) __________________________________________ " + sumDrops + " = " + removalMessage.getTotalAmount());
				
			} else {
				
				Long referenceNumber;
				String eventIndicator;  
				String caseMessage = "";
				/**
				 * an adjustment event is generated indicating the merchant number, 
				 * the amount to be credited or debited and the account concerned with the adjustment operation. 
				*/

		        Long leftLimit = (long) Math.pow(10, 11);
		        Long rightLimit = (long) Math.pow(10, 11);
		        referenceNumber = leftLimit + (long) (Math.random() * (9*rightLimit - leftLimit));
				eventIndicator = RandomStringUtils.randomAlphanumeric(15); 
				
				AdjustmentEvent removalEvents = objectMapper.convertValue(removalMessage, AdjustmentEvent.class);
				removalEvents.setTransferCurrency(removalMessage.getCurrency());
				removalEvents.setReferenceNumber(referenceNumber);
				removalEvents.setEventIndicator(eventIndicator);
				removalEvents.setSettlementDate(removalMessage.getTransmitionDate());
				
				/*
				 * 1 if a is greater than b
				 * -1 if b is less than b
				 * 0 if a is equal to b
				 */
				if(removalMessage.getTotalAmount().compareTo(sumDrops) == 1) {
					
					removalEvents.setTransferAmount(removalMessage.getTotalAmount().subtract(sumDrops));
					removalEvents.setTransferSign(TransferSign.C);
					String accountNumber = merchantDeviceProxy.getMerchantAccount(removalMessage.getMerchantNumber(), CREDITED_TYPE);
					removalEvents.setAccountNumber(accountNumber);
					caseMessage = "sum of the drops is smaller than the removal value. Means the customer has been short credited."; 
					
					// if the mode is on drop and the sum drops is less than the removal we need to credited merchant account.
					if(removalMessage.getMerchantSettlementMode() == DROP_SETTLEMENT_MODE) { 
						// TODO :: add into pre-cleared topic.
						CoreTransactionModel transactionPreCleared = new CoreTransactionModel();
						transactionPreCleared.setBagNumber(bagNumber);
						transactionPreCleared.setDeviceNumber(deviceNumber);
						transactionPreCleared.setTransactionId(transactionId);
						transactionPreCleared.setMerchantNumber(removalMessage.getMerchantNumber());
						transactionPreCleared.setTransmitionDate(currentRemovalTransactionDate);
						transactionPreCleared.setCurrency(removalMessage.getCurrency());
						transactionPreCleared.setTotalAmount(removalMessage.getTotalAmount().subtract(sumDrops));
						transactionPreCleared.setTypeCD(CREDITED_TYPE);
						preClearedTransaction.sendTransactionEvent(transactionPreCleared, PRODUCER_TOPIC_PRE_CLEARED);
					}
					
					
				} else if (removalMessage.getTotalAmount().compareTo(sumDrops) == -1) {
					
					// PowerCARD will create a case with no financial for a user consultation/investigation purposes.
//					removalEvents.setTransferAmount(sumDrops.subtract(removalMessage.getTotalAmount()));
//					removalEvents.setTransferSign(TransferSign.D);
//					String accountNumber = merchantDeviceProxy.getMerchantAccount(removalMessage.getMerchantNumber(), "debited");
//					removalEvents.setAccountNumber(accountNumber);
					caseMessage = "sum of drops is greater than the removal value. case Information has bean genereted";
					
				}
				
				// save adjustment event.
				adjustementEventRepository.save(removalEvents);
				
				// case information.
				CaseInformataion generatedCase = objectMapper.convertValue(removalMessage, CaseInformataion.class);
				generatedCase.setMessage(caseMessage);
				
				caseInformationRepository.save(generatedCase);
				
			}
		} 
	}
}
