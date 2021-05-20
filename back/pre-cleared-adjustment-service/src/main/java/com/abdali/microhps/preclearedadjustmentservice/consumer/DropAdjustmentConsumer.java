package com.abdali.microhps.preclearedadjustmentservice.consumer;

import static com.abdali.microhps.preclearedadjustmentservice.utils.Constants.CONSUMER_TOPIC_PRE_CLEARED;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.abdali.microhps.preclearedadjustmentservice.service.DropAdjustmentService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DropAdjustmentConsumer {
	
	DropAdjustmentService dropAdjustmentService;
	 
	 public DropAdjustmentConsumer(
			 DropAdjustmentService dropAdjustmentService
	 ) {
		 this.dropAdjustmentService = dropAdjustmentService;
		// TODO Auto-generated constructor stub
	}
	 
	@KafkaListener(topics = {CONSUMER_TOPIC_PRE_CLEARED})
   public void onMessage(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
       log.info("from consumer ConsumerRecord : {} ", consumerRecord );
       dropAdjustmentService.save(consumerRecord);
   }
}
