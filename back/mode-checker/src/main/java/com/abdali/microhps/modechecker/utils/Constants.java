package com.abdali.microhps.modechecker.utils;

public interface Constants {
		
		// Indicators 
		char DROP_INDICATOR = 'D';
		char REMOVAL_INDICATOR = 'R';
		char VERIFICATION_INDICATOR = 'V';
		
		//Settlement Mode
		String DROP_SETTLEMENT_MODE = "onDrop";
		String REMOVAL_SETTLEMENT_MODE = "onRemoval";
		String VERIFICATION_SETTLEMENT_MODE = "onCount";
		
		//Topics Names
		String TOPIC_PRECLEARED_SETTLEMENT_EVENTS = "pre-cleared-settlement-events";
		String TOPIC_REMOVAL_SETTLEMENT_EVENTS = "removal-transaction-settlement-events";
		String TOPIC_VERIFICATION_SETTLEMENT_EVENTS = "verification-transaction-settlement-events";
		
		// type of cleared transaction
		String CREDITED_TYPE = "credited";
}