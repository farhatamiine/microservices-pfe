package com.abdali.microhps.integrityservice.utils;

public interface Constants {
		
		// Indicators 
		char DROP_INDICATOR = 'D';
		char REMOVAL_INDICATOR = 'R';
		char VERIFICATION_INDICATOR = 'V';
		
		char NOTES_INDICATOR = 'N';
		char COINS_INDICATOR = 'C';
		
		// fix Length
		int TRANSACTION_ID_LENGTH = 9;
		int DEVICE_NUMBER = 6;
		int BAG_NUMBER = 14;
		int MERCHANT_NUMBER_LENGTH = 15;
		
		// INVALID MESSAGE EXCEPTION
		String MESSAGE_INVALID_CODE = "INV";
		String MESSAGE_INVALID_DESCRIPTION = "message Integrity Faillure";
		
		// MERCHANT NOT FOUND MESSAGE EXCEPTION
		String MESSAGE_MERCHANT_NOT_FOUND_CODE = "MNF";
		String MESSAGE_MERCHANT_NOT_FOUND_DESCRIPTION = "Merchant not found or invalid merchant status";
		
		// MERCHANT NOT MAPPED MESSAGE EXCEPTION
		String MESSAGE_MERCHANT_NOT_MAPPED_CODE = "MAP";
		String MESSAGE_MERCHANT_NOT_MAPPED_DESCRIPTION = "Wrong merchant Hierarchy";

		// DUPLICATED MESSAGE EXCEPTION
		String MESSAGE_DUPLICTAED_CODE = "DUP";
		String MESSAGE_DUPLICTAED_DESCRIPTION = "Duplicated message";

		// VERIFICATION MESSAGE EXCEPTION
		String MESSAGE_VERIFICATION_CODE = "NDP";
		String MESSAGE_VERIFICATION_DESCRIPTION = "No messages received in the system";
		
		// POWERCARD EXCEPTION
		String POWERCARD_VALIDATION_CODE = "PWC";
		String POWERCARD_VALIDATION_DESCRIPTION = "CHECK POWERCARD NOTIFICATION FOR MORE DETAILS";
		
		//Topics Names
		String TOPIC_DROP_NAME = "drop-transaction-events";
		String TOPIC_REMOVAL_NAME = "removal-transaction-events";
		String TOPIC_REMOVAL_VALIDATION_NAME = "removal-validation-events";
		String TOPIC_VERIFICATION_NAME = "verification-transaction-events";
		String TOPIC_VERIFICATION_VALIDATION_NAME = "verification-validation-events";
}
