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
	String TOPIC_DROP_SETTLEMENT_EVENTS = "drop-transaction-settlement-events";
	
}