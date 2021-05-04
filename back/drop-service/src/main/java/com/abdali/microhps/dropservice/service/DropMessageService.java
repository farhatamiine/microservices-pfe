package com.abdali.microhps.dropservice.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.abdali.microhps.dropservice.dto.DropCoreTransactionDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


public interface DropMessageService {

	void save(ConsumerRecord<Long,String> consumerRecord) throws JsonMappingException, JsonProcessingException;
	
	DropCoreTransactionDto findById(Long id);

	List<DropCoreTransactionDto> findByMerchantNumber(Long merchantNumber);
	
	List<DropCoreTransactionDto> findByDeviceNumber(String deviceNumber);
	
	List<DropCoreTransactionDto> findByBagNumber(String bagNumber);
	
	Map<String, Object> findAll(int page, int size);
	
	List<DropCoreTransactionDto> findByMerchantNumberAndBagNumberAndTransactionIdAndTransmitionDate(Long merchantNumber, String bagNumber, Integer transactionId, Instant transmitionDate);
	
	Boolean findByTransactionId(Integer transactionId);
}
