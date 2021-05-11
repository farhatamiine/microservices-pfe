package com.abdali.microhps.modechecker;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.abdali.microhps.modechecker.service.ModeCheckerService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ModeCheckerConsumer {

	ModeCheckerService modeCheckerService;
//	 
	 public ModeCheckerConsumer(
			 ModeCheckerService modeCheckerService
	 ) {
		 this.modeCheckerService = modeCheckerService;
	}
	 
	@KafkaListener(topics = {"global-adjustement-events"})
   public void onMessage(ConsumerRecord<Long, String> consumerRecord) throws JsonProcessingException {
       log.info("from consumer ConsumerRecord : {} ", consumerRecord );
        modeCheckerService.processSettlement(consumerRecord);
   }
	
}
