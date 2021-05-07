package com.abdali.microhps.verificationvalidationservice.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.abdali.microhps.verificationvalidationservice.service.VerificationValidationService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class verificationValidationConsumer {
	
	VerificationValidationService verificationValidationService;
	 
	@Autowired
	public verificationValidationConsumer(
			VerificationValidationService verificationValidationService
		) {
		// TODO Auto-generated constructor stub
		this.verificationValidationService = verificationValidationService;
	}
	
	@KafkaListener(topics = {"removal-validation-events"})
    public void onMessage(ConsumerRecord<Long, String> consumerRecord) throws JsonProcessingException {
        log.info("from consumer ConsumerRecord : {} ", consumerRecord );
        verificationValidationService.validateTransaction(consumerRecord);
    }
}
