package com.abdali.microhps.dropsadjustmentservice.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface DropAdjustmentService {

	void save(ConsumerRecord<Integer, String> consumerRecord) throws JsonMappingException, JsonProcessingException;
}
