package com.abdali.microhps.dropsadjustmentservice.service.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import com.abdali.microhps.dropsadjustmentservice.model.CoreTransactionModel;
import com.abdali.microhps.dropsadjustmentservice.service.DropAdjustmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DropAdjustmentServiceImpl implements DropAdjustmentService {
	
	ObjectMapper objectMapper;
	public DropAdjustmentServiceImpl(
			ObjectMapper objectMapper
			) {
		this.objectMapper = objectMapper;
	}
	
	public void save(ConsumerRecord<Integer, String> consumerRecord) throws JsonMappingException, JsonProcessingException {
		CoreTransactionModel dropTransaction = objectMapper.readValue(consumerRecord.value(), CoreTransactionModel.class);
		
	}
} 
