package com.abdali.microhps.removaladjustmentservice.utils;

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
	String PRODUCER_TOPIC_PRE_CLEARED = "pre-cleared-settlement-events";
	
	// type of cleared transaction
	String CREDITED_TYPE = "credited";
}
