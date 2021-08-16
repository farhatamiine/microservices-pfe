package com.abdali.microhps.merchantsettlementservice.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface MerchantSettlementService {
	
	void process(ConsumerRecord<Integer, String> consumerRecord);
}
