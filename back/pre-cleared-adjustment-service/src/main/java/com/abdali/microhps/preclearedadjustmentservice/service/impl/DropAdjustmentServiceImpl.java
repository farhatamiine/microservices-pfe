package com.abdali.microhps.preclearedadjustmentservice.service.impl;

import static com.abdali.microhps.preclearedadjustmentservice.utils.Constants.TOPIC_PRECLEARED_SETTLEMENT_EVENTS;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import com.abdali.microhps.preclearedadjustmentservice.model.CoreTransactionModel;
import com.abdali.microhps.preclearedadjustmentservice.model.PreClearedTransaction;
import com.abdali.microhps.preclearedadjustmentservice.producer.PreClearedTransactionProducer;
import com.abdali.microhps.preclearedadjustmentservice.proxy.MerchantDeviceProxy;
import com.abdali.microhps.preclearedadjustmentservice.repository.PreClearedTransactionRepository;
import com.abdali.microhps.preclearedadjustmentservice.service.DropAdjustmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DropAdjustmentServiceImpl implements DropAdjustmentService {
	
	MerchantDeviceProxy merchantDeviceProxy;
	PreClearedTransactionRepository preClearedTransactionRepository; 
	PreClearedTransactionProducer preClearedTransactionProducer;
	ObjectMapper objectMapper;
	
	public DropAdjustmentServiceImpl(
			ObjectMapper objectMapper,
			PreClearedTransactionRepository preClearedTransactionRepository,
			PreClearedTransactionProducer preClearedTransactionProducer,
			MerchantDeviceProxy merchantDeviceProxy,
			PreClearedTransaction preClearedTransaction
			) {
		this.objectMapper = objectMapper;
		this.merchantDeviceProxy = merchantDeviceProxy;
		this.preClearedTransactionRepository = preClearedTransactionRepository;
		this.preClearedTransactionProducer = preClearedTransactionProducer;
	}
	
	public void save(ConsumerRecord<Integer, String> consumerRecord) throws JsonMappingException, JsonProcessingException {
		 
		// Credited Amount :: to change .
		CoreTransactionModel clearedTransaction = objectMapper.readValue(consumerRecord.value(), CoreTransactionModel.class);
		
		PreClearedTransaction clearedDropTransaction = objectMapper.convertValue(clearedTransaction, PreClearedTransaction.class);
		
		clearedDropTransaction.setCreaditedAmount(clearedTransaction.getTotalAmount()); 
		
		//Account Number
		String accountNumber = merchantDeviceProxy.getMerchantCreditedAccount(clearedDropTransaction.getMerchantNumber(), clearedTransaction.getTypeCD());
		clearedDropTransaction.setAccountNumber(accountNumber);  
		
//		PreClearedTransaction clearedDropTransaction = new PreClearedTransaction();
		 
		PreClearedTransaction result = preClearedTransactionRepository.save(clearedDropTransaction);
		
		// add to drop adjustment topic. just if mode is removal or it will be duplicated.
		preClearedTransactionProducer.sendTransactionEvent(clearedDropTransaction, TOPIC_PRECLEARED_SETTLEMENT_EVENTS); 
	}
} 