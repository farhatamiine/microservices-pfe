package com.abdali.microhps.integrityservice.validation;

import static com.abdali.microhps.integrityservice.utils.Constants.BAG_NUMBER;
import static com.abdali.microhps.integrityservice.utils.Constants.COINS_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.DEVICE_NUMBER;
import static com.abdali.microhps.integrityservice.utils.Constants.DROP_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.NOTES_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.REMOVAL_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.TRANSACTION_ID_LENGTH;
import static com.abdali.microhps.integrityservice.utils.Constants.VERIFICATION_INDICATOR;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.abdali.microhps.integrityservice.exceptions.MessageFormatException;


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
			char Indicator, 
			String MerchantNumber, 
			String Device, 
			String Bag, 
			char Container, 
			String Sequence, 
			String TransmitionDate, 
			String TransactionId) {
		
		Boolean messageReturn = false;
		
		// STEP 1 - check Indicator. -- it can be one of others status ...
		if(Indicator == DROP_INDICATOR || Indicator == REMOVAL_INDICATOR || Indicator == VERIFICATION_INDICATOR) {		
			// STEP 3 && STEP 4 - check for Device Number and bag NUmber.
			if(Device.length() == DEVICE_NUMBER && Bag.length() == BAG_NUMBER) {
				// STEP 5 - check for container
				if(Container == NOTES_INDICATOR || Container == COINS_INDICATOR) {	
					// STEP 7 - verify date using expression not -- TODO : change this validation
					String regex = "^[0-9]{4}-[0-9]{2}-[0-9]{4}:[0-9]{2}:[0-9]{2}:[0-9]{5}$";
			        Pattern pattern = Pattern.compile(regex);
			        Matcher matcher = pattern.matcher((CharSequence)TransmitionDate);  
			        if(matcher.matches()) {		
			        	// STEP 8 - check for transaction ID - we need to check just if its present.
			        	if(TransactionId.length() == TRANSACTION_ID_LENGTH) {
			        		return messageReturn = true;
			        	}
//			        	throw new NoDataFoundException("Transaction Id not present " + TransactionId);
			        	throw new MessageFormatException();
			        } 
//			        throw new NoDataFoundException("date time format invalid " + matcher.matches() + TransmitionDate);
			        throw new MessageFormatException();
				}
//				throw new NoDataFoundException("There is problem with Container Indicator ");
				throw new MessageFormatException();
			}  
//			throw new NoDataFoundException(" device number " + Device.length() + "____");
			throw new MessageFormatException();
		} else { 
//			throw new NoDataFoundException("the Indicator is not valid");
			throw new MessageFormatException();
		}
	}
}
