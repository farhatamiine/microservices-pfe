package com.abdali.microhps.removalvalidationservice.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.abdali.microhps.removalvalidationservice.serivce.RemovalValidationService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class removalValidationConsmer {
	 RemovalValidationService removalValidationService;
	 
	 public removalValidationConsmer(
			 RemovalValidationService removalValidationService
	 ) {
		 this.removalValidationService = removalValidationService;
		// TODO Auto-generated constructor stub
	}
	 
	@KafkaListener(topics = {"removal-validation-events"})
    public void onMessage(ConsumerRecord<Long, String> consumerRecord) throws JsonProcessingException {
        log.info("from consumer ConsumerRecord : {} ", consumerRecord );
        removalValidationService.validateTransaction(consumerRecord);
    }
}
