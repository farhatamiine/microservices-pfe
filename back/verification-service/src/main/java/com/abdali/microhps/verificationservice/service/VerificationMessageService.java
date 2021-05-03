package com.abdali.microhps.verificationservice.service;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.abdali.microhps.verificationservice.dto.VerificationCoreTransactionDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


public interface VerificationMessageService {

	void save(ConsumerRecord<Long, String> consumerRecord) throws JsonMappingException, JsonProcessingException;;

	VerificationCoreTransactionDto findById(Long id);
	
	List<VerificationCoreTransactionDto> findByDeviceNumber(String deviceNumber);
	
	List<VerificationCoreTransactionDto> findByBagNumber(String bagNumber);
	
	List<VerificationCoreTransactionDto> findAll();
}
