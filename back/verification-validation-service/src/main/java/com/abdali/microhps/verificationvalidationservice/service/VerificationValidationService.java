package com.abdali.microhps.verificationvalidationservice.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface VerificationValidationService {
	void validateTransaction(ConsumerRecord<Long,String> consumerRecord) throws JsonMappingException, JsonProcessingException;
}
