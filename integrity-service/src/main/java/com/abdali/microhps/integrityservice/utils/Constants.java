package com.abdali.microhps.integrityservice.utils;

import java.util.Arrays;
import java.util.List;

public interface Constants {
	// List of Outlet to check for it -- MerchantNotFound Service Validation.
		List<String> POWERCARD_OUTLET_STATUS = Arrays.asList("closed", "suspended", "deactivated");
		
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
}
