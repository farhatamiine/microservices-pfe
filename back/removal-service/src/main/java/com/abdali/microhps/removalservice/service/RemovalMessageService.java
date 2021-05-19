package com.abdali.microhps.removalservice.service;

import java.time.Instant;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord; 
 
import com.abdali.microhps.removalservice.dto.RemovalCoreMessageDto;
import com.abdali.microhps.removalservice.model.RemovalCoreMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


public interface RemovalMessageService {

	void save(ConsumerRecord<Long,String> consumerRecord) throws JsonMappingException, JsonProcessingException;

	RemovalCoreMessageDto findById(Long id);
	
	List<RemovalCoreMessageDto> findByDeviceNumber(String deviceNumber);
	
	List<RemovalCoreMessageDto> findByBagNumber(String bagNumber);
	
	List<RemovalCoreMessageDto> findAll();
	
	Boolean findByTransactionId(Integer transactionId);
	
	RemovalCoreMessage findRemovalTransaction(String deviceNumber, String bagNumber, Integer transactionId);

	RemovalCoreMessage removalBetwwenDates(String deviceNumber, String bagNumber, Instant startDate, Instant endDate);
	
	Instant findLastOneBydeviceAndBag(String deviceNumber, String bagNumber);
}
