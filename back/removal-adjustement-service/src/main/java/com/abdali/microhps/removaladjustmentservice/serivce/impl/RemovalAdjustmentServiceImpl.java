package com.abdali.microhps.removaladjustmentservice.serivce.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant; 
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.removaladjustmentservice.model.AdjustementEvent;
import com.abdali.microhps.removaladjustmentservice.model.CoreTransactionModel;
import com.abdali.microhps.removaladjustmentservice.producer.PreClearedTransaction;
import com.abdali.microhps.removaladjustmentservice.proxy.DropTransactionProxy;
import com.abdali.microhps.removaladjustmentservice.proxy.RemovalTransactionProxy;
import com.abdali.microhps.removaladjustmentservice.repository.AdjustementEventRepository;
import com.abdali.microhps.removaladjustmentservice.serivce.RemovalAdjustmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RemovalAdjustmentServiceImpl implements RemovalAdjustmentService {
	
	AdjustementEventRepository adjustementEventRepository;
	RemovalTransactionProxy removalTransactionProxy;
	DropTransactionProxy dropTransactionService;
    ObjectMapper objectMapper;
    PreClearedTransaction preClearedTransaction;
	
	@Autowired
	public RemovalAdjustmentServiceImpl(
			AdjustementEventRepository adjustementEventRepository,
			RemovalTransactionProxy removalTransactionProxy,
			DropTransactionProxy dropTransactionProxy,
		    ObjectMapper objectMapper,
		    PreClearedTransaction preClearedTransaction
			) {
		this.adjustementEventRepository = adjustementEventRepository;
		this.removalTransactionProxy = removalTransactionProxy;
		this.dropTransactionService = dropTransactionProxy;
	    this.objectMapper = objectMapper;
	    this.preClearedTransaction = preClearedTransaction;
	} 
	
	/*
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
		
		CoreTransactionModel lastMessage = removalTransactionProxy.findRemovalTransaction(deviceNumber, bagNumber, transactionId);
		
		if(lastMessage != null) {
			
			Instant lastRemovalTransactionDate = lastMessage.getTransmitionDate();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");  
			Instant currentRemovalTransactionDate = removalMessage.getTransmitionDate();	
			
			List<CoreTransactionModel> listDrops = dropTransactionService.listDropsBetwwenDates(deviceNumber, bagNumber, formatter.format(lastRemovalTransactionDate), formatter.format(currentRemovalTransactionDate));
			BigDecimal sumDrops = new BigDecimal(0);
			for (CoreTransactionModel e : listDrops) { 
				sumDrops = sumDrops.add(e.getTotalAmount());
			}
			// add value to merchant Number - we suppose that removal transaction correspond to one merchant.
			removalMessage.setMerchantNumber(listDrops.get(0).getMerchantNumber());
			
			// In Both cases we need to save into pre-cleared Table. 
			preClearedTransaction.sendTransactionEvent(removalMessage, "pre-cleared-transaction-topic");
			
			if(removalMessage.getTotalAmount().equals(sumDrops) ) {
				log.info("removal Amount is equal sum(Drops) __________________________________________ " + sumDrops + " = " + removalMessage.getTotalAmount());
			} else {
				/**
				 * an adjustment event is generated indicating the merchant number, 
				 * the amount to be credited or debited and the account concerned with the adjustment operation. 
				*/
				AdjustementEvent removalEvents = objectMapper.convertValue(removalMessage, AdjustementEvent.class);
				/*
				 * 1 if a is greater than b
				 * -1 if b is less than b
				 * 0 if a is equal to b
				 */
				if(removalMessage.getTotalAmount().compareTo(sumDrops) == 1) {
					removalEvents.setCreaditedAmount(removalMessage.getTotalAmount().subtract(sumDrops));
				} else if (removalMessage.getTotalAmount().compareTo(sumDrops) == -1){
					removalEvents.setDebitedAmount(sumDrops.subtract(removalMessage.getTotalAmount()));
				}
				adjustementEventRepository.save(removalEvents);
				// TODO :: add to Pre cleared here too.
			}
		} 
	}
}
