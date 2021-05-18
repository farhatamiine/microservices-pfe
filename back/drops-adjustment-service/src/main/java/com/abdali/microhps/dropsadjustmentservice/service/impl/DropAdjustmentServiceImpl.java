package com.abdali.microhps.dropsadjustmentservice.service.impl;

import java.math.BigDecimal;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;
 
import com.abdali.microhps.dropsadjustmentservice.model.CoreTransactionModel;
import com.abdali.microhps.dropsadjustmentservice.model.PreClearedTransaction;
import com.abdali.microhps.dropsadjustmentservice.proxy.MerchantDeviceProxy;
import com.abdali.microhps.dropsadjustmentservice.repository.PreClearedTransactionRepository;
import com.abdali.microhps.dropsadjustmentservice.service.DropAdjustmentService; 
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DropAdjustmentServiceImpl implements DropAdjustmentService {
	
	MerchantDeviceProxy merchantDeviceProxy;
	PreClearedTransactionRepository preClearedTransactionRepository; 
	ObjectMapper objectMapper;
	
	public DropAdjustmentServiceImpl(
			ObjectMapper objectMapper,
			PreClearedTransactionRepository preClearedTransactionRepository,
			MerchantDeviceProxy merchantDeviceProxy
			) {
		this.objectMapper = objectMapper;
		this.merchantDeviceProxy = merchantDeviceProxy;
		this.preClearedTransactionRepository = preClearedTransactionRepository;
	}
	
	public void save(ConsumerRecord<Integer, String> consumerRecord) throws JsonMappingException, JsonProcessingException {
		 
		// Credited Amount :: to change .
		CoreTransactionModel clearedTransaction = objectMapper.readValue(consumerRecord.value(), CoreTransactionModel.class);
		
		PreClearedTransaction clearedDropTransaction = objectMapper.convertValue(clearedTransaction, PreClearedTransaction.class);
		
		clearedDropTransaction.setCreaditedAmount(clearedTransaction.getTotalAmount()); 
		
		//Account Number
		String accountNumber = merchantDeviceProxy.getMerchantCreditedAccount(clearedDropTransaction.getMerchantNumber(), "credited");
		clearedDropTransaction.setAccountNumber(accountNumber); 
//		
//		PreClearedTransaction clearedDropTransaction = new PreClearedTransaction();
		 
		PreClearedTransaction result = preClearedTransactionRepository.save(clearedDropTransaction);
		
	}
} 
