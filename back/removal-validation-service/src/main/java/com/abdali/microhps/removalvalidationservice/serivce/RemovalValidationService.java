package com.abdali.microhps.removalvalidationservice.serivce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface RemovalValidationService {
	void validateTransaction(ConsumerRecord<Long,String> consumerRecord) throws JsonMappingException, JsonProcessingException;
}
