package com.abdali.microhps.merchantsettlementservice.service.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import com.abdali.microhps.merchantsettlementservice.service.MerchantSettlementService;

@Service
public class MerchantSettlementServiceImpl implements MerchantSettlementService {

	public void process(ConsumerRecord<Integer, String> consumerRecord) {
		
	}
}
