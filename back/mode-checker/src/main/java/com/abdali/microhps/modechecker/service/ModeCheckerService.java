package com.abdali.microhps.modechecker.service;

import static com.abdali.microhps.modechecker.utils.Constants.DROP_INDICATOR;
import static com.abdali.microhps.modechecker.utils.Constants.REMOVAL_INDICATOR;
import static com.abdali.microhps.modechecker.utils.Constants.VERIFICATION_INDICATOR;

import static com.abdali.microhps.modechecker.utils.Constants.DROP_SETTLEMENT_MODE;
import static com.abdali.microhps.modechecker.utils.Constants.REMOVAL_SETTLEMENT_MODE;
import static com.abdali.microhps.modechecker.utils.Constants.VERIFICATION_SETTLEMENT_MODE;

import static com.abdali.microhps.modechecker.utils.Constants.TOPIC_PRECLEARED_SETTLEMENT_EVENTS;
import static com.abdali.microhps.modechecker.utils.Constants.TOPIC_REMOVAL_SETTLEMENT_EVENTS;
import static com.abdali.microhps.modechecker.utils.Constants.TOPIC_VERIFICATION_SETTLEMENT_EVENTS;

import static com.abdali.microhps.modechecker.utils.Constants.CREDITED_TYPE;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.modechecker.model.Transaction;
import com.abdali.microhps.modechecker.model.TransactionModel;
import com.abdali.microhps.modechecker.producer.SettlementTransactionProducer;
import com.abdali.microhps.modechecker.proxy.DeviceMerchantProxy;
import com.abdali.microhps.modechecker.proxy.DropTransactionProxy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ModeCheckerService {

	DeviceMerchantProxy deviceMerchantProxy;
	DropTransactionProxy dropTransactionProxy;
	SettlementTransactionProducer settlementTransactionProducer;
    ObjectMapper objectMapper; 
	
	@Autowired
	public ModeCheckerService(
			DeviceMerchantProxy deviceMerchantProxy,
			DropTransactionProxy dropTransactionProxy,
			SettlementTransactionProducer settlementTransactionProducer,
		    ObjectMapper objectMapper
			) {
		this.deviceMerchantProxy = deviceMerchantProxy;
		this.settlementTransactionProducer = settlementTransactionProducer;
		this.dropTransactionProxy = dropTransactionProxy;
	    this.objectMapper = objectMapper;
	} 
	
	public void processSettlement(ConsumerRecord<Long, String> consumerRecord) throws JsonMappingException, JsonProcessingException {
		
		// Save into topic depend on merchant mode.
		Transaction transactionCore = objectMapper.readValue(consumerRecord.value(), Transaction.class);
		Long merchantNumber;
		String merchantSettlementMode;
		String deviceNumber;
		String bagNumber;
		
		switch (transactionCore.getIndicator()) {
			case DROP_INDICATOR:
				merchantNumber = transactionCore.getDropTransaction().getMerchantNumber(); 
				merchantSettlementMode = deviceMerchantProxy.returnMerchantSettlementMode(merchantNumber);
				if(merchantSettlementMode == DROP_SETTLEMENT_MODE) {
					TransactionModel transactionSettlement = objectMapper.convertValue(transactionCore, TransactionModel.class);
					transactionSettlement.setMerchantNumber(merchantNumber);
					transactionSettlement.setTypeCD(CREDITED_TYPE);
					settlementTransactionProducer.sendTransactionEvent(transactionSettlement, TOPIC_PRECLEARED_SETTLEMENT_EVENTS);
				}
				break;
			case REMOVAL_INDICATOR:
				//get merchant number from last drops correspond to this removal.
				deviceNumber = transactionCore.getDeviceNumber();
				bagNumber = transactionCore.getBagNumber();
				// get last drop message for this removal.
				merchantNumber = dropTransactionProxy.getMerchantNumber(deviceNumber, bagNumber);
				merchantSettlementMode = deviceMerchantProxy.returnMerchantSettlementMode(merchantNumber);
				if(merchantSettlementMode == REMOVAL_SETTLEMENT_MODE || merchantSettlementMode == DROP_SETTLEMENT_MODE) {
					TransactionModel transactionSettlement = objectMapper.convertValue(transactionCore, TransactionModel.class);
					transactionSettlement.setMerchantNumber(merchantNumber);
					transactionSettlement.setMerchantSettlementMode(merchantSettlementMode);
					settlementTransactionProducer.sendTransactionEvent(transactionSettlement, TOPIC_REMOVAL_SETTLEMENT_EVENTS); 
					// TODO: add list of drops to topic. will did in removal adjustment service.
				}
				break;
			case VERIFICATION_INDICATOR: 
				deviceNumber = transactionCore.getDeviceNumber();
				bagNumber = transactionCore.getBagNumber(); 
				merchantNumber = dropTransactionProxy.getMerchantNumber(deviceNumber, bagNumber);
				merchantSettlementMode = deviceMerchantProxy.returnMerchantSettlementMode(merchantNumber);
				if(merchantSettlementMode == VERIFICATION_SETTLEMENT_MODE || merchantSettlementMode == DROP_SETTLEMENT_MODE) {
					TransactionModel transactionSettlement = objectMapper.convertValue(transactionCore, TransactionModel.class);
					transactionSettlement.setMerchantNumber(merchantNumber);
					transactionSettlement.setMerchantSettlementMode(merchantSettlementMode);
					settlementTransactionProducer.sendTransactionEvent(transactionSettlement, TOPIC_VERIFICATION_SETTLEMENT_EVENTS);
				}
				break;
		}
		
	}
}
