package com.abdali.microhps.integrityservice.validation;

import static com.abdali.microhps.integrityservice.utils.Constants.BAG_NUMBER;
import static com.abdali.microhps.integrityservice.utils.Constants.COINS_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.DEVICE_NUMBER;
import static com.abdali.microhps.integrityservice.utils.Constants.DROP_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.MESSAGE_INVALID_CODE;
import static com.abdali.microhps.integrityservice.utils.Constants.MESSAGE_INVALID_DESCRIPTION;
import static com.abdali.microhps.integrityservice.utils.Constants.NOTES_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.REMOVAL_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.TRANSACTION_ID_LENGTH;
import static com.abdali.microhps.integrityservice.utils.Constants.VERIFICATION_INDICATOR;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.abdali.microhps.integrityservice.exceptions.IntegrityException;


//- step 1 : Message indicator is not recognised or not present. -- must equal D || R || V
//- step 2 : Merchant number is not present. (N/A for Removal and verification message types). // check if its 15 integer // checked inside controller
//- step 3 : Device number is not present. // 6 digits
//- step 4 : Bag number is not present. // 14 number 
//- step 5 : Container indicator in not recognised. // char N(notes) || C(coins).
//- step 6 : Sequence number is not present. // available just for drop messages and Removal and its start form 1 when the bag is changed increase until next bag changed again.
//- step 7 : Transmission date is not present or not correctly formatted. // check for date format
//- step 8 : Transaction id is not present. // check for it inside DB its unique - but i think that they say to check just if its present.

@Service
public class MessageFormat {
	
//	private DateTimeFormatter dateFormatter;
	
	public Boolean checkMessageFormat(
			char indicator, 
			Long merchantNumber, 
			String deviceNumber, 
			String bagNumber, 
			char containerType, 
			int sequenceNumber, 
			String transmitionDate, 
			String transactionId) {
		
		// STEP 1 - check Indicator. -- it can be one of others status ...
		if(indicator == DROP_INDICATOR || indicator == REMOVAL_INDICATOR || indicator == VERIFICATION_INDICATOR) {		
			// STEP 3 && STEP 4 - check for Device Number and bag Number.
			if(deviceNumber.length() == DEVICE_NUMBER && bagNumber.length() == BAG_NUMBER) {
				// STEP 5 - check for container
				if(containerType == NOTES_INDICATOR || containerType == COINS_INDICATOR) {	
					final DateTimeFormatter formatter = DateTimeFormatter
			                .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
			                .withZone(ZoneId.systemDefault());
					try {
						Instant.from(formatter.parse(transmitionDate));
					} catch (Exception e) {
						throw new IntegrityException(MESSAGE_INVALID_CODE, MESSAGE_INVALID_DESCRIPTION);
					} 
					// STEP 8 - check for transaction ID - we need to check just if its present.
		        	if(transactionId.length() == TRANSACTION_ID_LENGTH) {
		        		return true;
		        	}
					throw new IntegrityException(MESSAGE_INVALID_CODE, MESSAGE_INVALID_DESCRIPTION);
				}
				throw new IntegrityException(MESSAGE_INVALID_CODE, MESSAGE_INVALID_DESCRIPTION);
			}  
			throw new IntegrityException(MESSAGE_INVALID_CODE, MESSAGE_INVALID_DESCRIPTION);
		}
		throw new IntegrityException(MESSAGE_INVALID_CODE, MESSAGE_INVALID_DESCRIPTION);
	}
}
