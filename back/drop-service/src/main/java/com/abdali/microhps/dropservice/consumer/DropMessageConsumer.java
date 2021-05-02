package com.abdali.microhps.dropservice.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.abdali.microhps.dropservice.service.DropMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DropMessageConsumer {
	
	DropMessageService dropMessageService;
    
    @Autowired
    public DropMessageConsumer(DropMessageService dropMessageService) {
    	this.dropMessageService = dropMessageService;
    }

    @KafkaListener(topics = {"drop-transaction-events"})
    public void onMessage(ConsumerRecord<Long, String> consumerRecord) throws JsonProcessingException {
        log.info("ConsumerRecord : {} ", consumerRecord );
        dropMessageService.save(consumerRecord);

    }
    
}
