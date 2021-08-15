package com.abdali.microhps.verificationadjustmentservice.utils;

public interface Constants {
		
		// Indicators  
		char REMOVAL_INDICATOR = 'R'; 
		
		//Topics Names
		String TOPIC_REMOVAL_NAME = "removal-transaction-events"; 

		//Topics Names 
		String PRODUCER_TOPIC_PRE_CLEARED = "pre-cleared-settlement-events";
		
		// type of cleared transaction
		String CREDITED_TYPE = "credited";
		String DEBITED_TYPE = "debited";
		
		//Settlement Mode
		String DROP_SETTLEMENT_MODE = "onDrop";
		String REMOVAL_SETTLEMENT_MODE = "onRemoval";
		String VERIFICATION_SETTLEMENT_MODE = "onCount";
}