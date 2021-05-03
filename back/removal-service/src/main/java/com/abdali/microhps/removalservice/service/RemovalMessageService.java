package com.abdali.microhps.removalservice.service;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.abdali.microhps.removalservice.dto.RemovalCoreMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


public interface RemovalMessageService {

	void save(ConsumerRecord<Long,String> consumerRecord) throws JsonMappingException, JsonProcessingException;

	RemovalCoreMessageDto findById(Long id);
	
	List<RemovalCoreMessageDto> findByDeviceNumber(String deviceNumber);
	
	List<RemovalCoreMessageDto> findByBagNumber(String bagNumber);
	
	List<RemovalCoreMessageDto> findAll();
}
