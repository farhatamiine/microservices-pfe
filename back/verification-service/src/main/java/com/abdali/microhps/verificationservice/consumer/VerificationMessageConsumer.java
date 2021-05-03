package com.abdali.microhps.verificationservice.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.abdali.microhps.verificationservice.service.VerificationMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class VerificationMessageConsumer {
	
	VerificationMessageService verificationMessageService;
    
    @Autowired
    public VerificationMessageConsumer(VerificationMessageService verificationMessageService) {
    	this.verificationMessageService = verificationMessageService;
    }

    @KafkaListener(topics = {"verification-transaction-events"})
    public void onMessage(ConsumerRecord<Long, String> consumerRecord) throws JsonProcessingException {
        log.info("ConsumerRecord : {} ", consumerRecord );
        verificationMessageService.save(consumerRecord);

    }
    
}
