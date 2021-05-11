package com.abdali.microhps.removaladjustmentservice.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.abdali.microhps.removaladjustmentservice.serivce.RemovalAdjustmentService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class removalAdjustmentConsumer {
	 RemovalAdjustmentService removalValidationService;
	 
	 public removalAdjustmentConsumer(
			 RemovalAdjustmentService removalValidationService
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
