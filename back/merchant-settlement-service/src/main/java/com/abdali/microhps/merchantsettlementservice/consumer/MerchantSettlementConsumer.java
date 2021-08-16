package com.abdali.microhps.merchantsettlementservice.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.abdali.microhps.merchantsettlementservice.service.MerchantSettlementService; 
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MerchantSettlementConsumer {
	
	MerchantSettlementService merchantSettlementService;
	 
	 public MerchantSettlementConsumer(
			 MerchantSettlementService merchantSettlementService
	 ) {
		 this.merchantSettlementService = merchantSettlementService;
		// TODO Auto-generated constructor stub
	}
	 
	@KafkaListener(topics = "merchant-settlement-events")
  public void onMessage(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
      log.info("from consumer ConsumerRecord : {} ", consumerRecord );
      merchantSettlementService.process(consumerRecord);
  }
}
