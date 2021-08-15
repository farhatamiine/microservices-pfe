package com.abdali.microhps.integrityservice.validation;

import static com.abdali.microhps.integrityservice.utils.Constants.BAG_NUMBER;
import static com.abdali.microhps.integrityservice.utils.Constants.COINS_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.DEVICE_NUMBER;
import static com.abdali.microhps.integrityservice.utils.Constants.DROP_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.MERCHANT_NUMBER_LENGTH;
import static com.abdali.microhps.integrityservice.utils.Constants.MESSAGE_INVALID_CODE;
import static com.abdali.microhps.integrityservice.utils.Constants.MESSAGE_INVALID_DESCRIPTION;
import static com.abdali.microhps.integrityservice.utils.Constants.NOTES_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.REMOVAL_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.TRANSACTION_ID_LENGTH;
import static com.abdali.microhps.integrityservice.utils.Constants.VERIFICATION_INDICATOR;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import com.abdali.microhps.integrityservice.exceptions.IntegrityException;
import com.abdali.microhps.integrityservice.model.Transaction;
import com.abdali.microhps.integrityservice.service.TransactionBuilder;
import com.abdali.microhps.integrityservice.service.TransactionService;


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
	
	private TransactionService transactionService;
	private TransactionBuilder transactionBuilder;
	
	public MessageFormat(
			TransactionService transactionService,
			TransactionBuilder transactionBuilder
			) {
		this.transactionService = transactionService;
		this.transactionBuilder = transactionBuilder;
	}
	
	public Boolean checkMessageFormat(
		char indicator, 
		String merchantNumber, 
		String deviceNumber, 
		String bagNumber, 
		char containerType, 
		int sequenceNumber, 
		String transmitionDate, 
		String transactionId,
		String[] messageSplited,
		String message) {		
		// STEP 1 - check Indicator. -- it can be one of others status ...
		if(indicator == DROP_INDICATOR || indicator == REMOVAL_INDICATOR || indicator == VERIFICATION_INDICATOR) {	

			if(indicator == DROP_INDICATOR && messageSplited[1].length() != MERCHANT_NUMBER_LENGTH && !messageSplited[1].matches("[0-9_]+")) {
				Transaction transaction = transactionBuilder.transactionBuild(containerType, messageSplited, message);
				transactionService.transactionCreate(transaction);
				throw new IntegrityException(MESSAGE_INVALID_CODE, MESSAGE_INVALID_DESCRIPTION);
			}
			// STEP 3 && STEP 4 - check for Device Number and bag Number.
			if(deviceNumber.length() == DEVICE_NUMBER && bagNumber.length() == BAG_NUMBER) {
				// STEP 5 - check for container
				if(containerType == NOTES_INDICATOR || containerType == COINS_INDICATOR) {	
					// STEP 8 - check for transaction ID - we need to check just if its present.
					if(transactionId.length() == TRANSACTION_ID_LENGTH) {
						final DateTimeFormatter formatter = DateTimeFormatter
								.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
								.withZone(ZoneId.systemDefault());
						try {
							Instant.from(formatter.parse(transmitionDate));
						} catch (Exception e) {
							Transaction transaction = transactionBuilder.transactionBuild(containerType, messageSplited, message);
							transactionService.transactionCreate(transaction);
							throw new IntegrityException(MESSAGE_INVALID_CODE, MESSAGE_INVALID_DESCRIPTION);
						} 
						return true;
					}
//						throw new IntegrityException(MESSAGE_INVALID_CODE, "4444444444444444444");
				}
//					throw new IntegrityException(MESSAGE_INVALID_CODE, "333333333333");
			}  
//				throw new IntegrityException(MESSAGE_INVALID_CODE, "2222222222");
		}
//			throw new IntegrityException(MESSAGE_INVALID_CODE, "11111111");
//		throw new IntegrityException(MESSAGE_INVALID_CODE, "1111111100");
		Transaction transaction = transactionBuilder.transactionBuild(containerType, messageSplited, message);
		transactionService.transactionCreate(transaction);
		throw new IntegrityException(MESSAGE_INVALID_CODE, MESSAGE_INVALID_DESCRIPTION);
	}
}
