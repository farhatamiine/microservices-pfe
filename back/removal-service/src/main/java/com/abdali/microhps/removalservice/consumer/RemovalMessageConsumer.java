package com.abdali.microhps.removalservice.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.abdali.microhps.removalservice.service.RemovalMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RemovalMessageConsumer {

	RemovalMessageService removalMessageService;
    
    @Autowired
    public RemovalMessageConsumer(RemovalMessageService removalMessageService) {
    	this.removalMessageService = removalMessageService;
    }

    @KafkaListener(topics = {"removal-transaction-events"})
    public void onMessage(ConsumerRecord<Long, String> consumerRecord) throws JsonProcessingException {
        log.info("ConsumerRecord : {} ", consumerRecord );
        removalMessageService.save(consumerRecord);

    }
}
