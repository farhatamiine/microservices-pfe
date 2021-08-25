package com.abdali.microhps.preclearedadjustmentservice.service.impl;

import static com.abdali.microhps.preclearedadjustmentservice.utils.Constants.PRODUCER_TOPIC_SETTLEMENT_EVENTS;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

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
		PreClearedTransaction clearedTransaction = objectMapper.readValue(consumerRecord.value(), PreClearedTransaction.class);
		
//		PreClearedTransaction finalClearedTransaction = objectMapper.convertValue(clearedTransaction, PreClearedTransaction.class);
		
//		clearedTransaction.setCreaditedAmount(clearedTransaction.getTotalAmount()); 
		
		//Account Number
		String accountNumber = merchantDeviceProxy.getMerchantAccount(clearedTransaction.getMerchantNumber(), clearedTransaction.getTypeCD());
		clearedTransaction.setAccountNumber(accountNumber);  
		
//		PreClearedTransaction clearedDropTransaction = new PreClearedTransaction();
		 
		PreClearedTransaction result = preClearedTransactionRepository.save(clearedTransaction);
		
		// send to settlement event
		preClearedTransactionProducer.sendTransactionEvent(clearedTransaction, PRODUCER_TOPIC_SETTLEMENT_EVENTS); 
	}
} 
