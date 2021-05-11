package com.abdali.microhps.modechecker.service;

import static com.abdali.microhps.modechecker.utils.Constants.DROP_INDICATOR;
import static com.abdali.microhps.modechecker.utils.Constants.REMOVAL_INDICATOR;
import static com.abdali.microhps.modechecker.utils.Constants.VERIFICATION_INDICATOR;

import static com.abdali.microhps.modechecker.utils.Constants.DROP_SETTLEMENT_MODE;
import static com.abdali.microhps.modechecker.utils.Constants.REMOVAL_SETTLEMENT_MODE;
import static com.abdali.microhps.modechecker.utils.Constants.VERIFICATION_SETTLEMENT_MODE;

import static com.abdali.microhps.modechecker.utils.Constants.TOPIC_DROP_SETTLEMENT_EVENTS;
import static com.abdali.microhps.modechecker.utils.Constants.TOPIC_REMOVAL_SETTLEMENT_EVENTS;
import static com.abdali.microhps.modechecker.utils.Constants.TOPIC_VERIFICATION_SETTLEMENT_EVENTS;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.modechecker.model.Transaction;
import com.abdali.microhps.modechecker.model.TransactionModel;
import com.abdali.microhps.modechecker.producer.SettlementTransactionProducer;
import com.abdali.microhps.modechecker.proxy.DeviceMerchantProxy; 
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ModeCheckerService {

	DeviceMerchantProxy deviceMerchantProxy;
	SettlementTransactionProducer settlementTransactionProducer;
    ObjectMapper objectMapper; 
	
	@Autowired
	public ModeCheckerService(
			DeviceMerchantProxy deviceMerchantProxy,
			SettlementTransactionProducer settlementTransactionProducer,
		    ObjectMapper objectMapper
			) {
		this.deviceMerchantProxy = deviceMerchantProxy;
		this.settlementTransactionProducer = settlementTransactionProducer;
	    this.objectMapper = objectMapper;
	} 
	
	public void processSettlement(ConsumerRecord<Long, String> consumerRecord) throws JsonMappingException, JsonProcessingException {
		
		// Sauvegarde into topic depend on merchant mode
		Transaction transactionCore = objectMapper.readValue(consumerRecord.value(), Transaction.class);
		
		switch (transactionCore.getIndicator()) {
			case DROP_INDICATOR:
				Long merchantNumber = transactionCore.getDropTransaction().getMerchantNumber(); 
				String merchantSettlementMode = deviceMerchantProxy.returnMerchantSettlementMode(merchantNumber);
				if(merchantSettlementMode == DROP_SETTLEMENT_MODE) {
					TransactionModel transactionSettlement = objectMapper.convertValue(transactionCore, TransactionModel.class);
					transactionSettlement.setMerchantNumber(merchantNumber);
					settlementTransactionProducer.sendTransactionEvent(transactionSettlement, TOPIC_DROP_SETTLEMENT_EVENTS);
				}
				break;
			case REMOVAL_INDICATOR:
				//get merchant number from drops correspond to this removal.
				break;
			case VERIFICATION_INDICATOR:
				break;
		}
		
	}
}
