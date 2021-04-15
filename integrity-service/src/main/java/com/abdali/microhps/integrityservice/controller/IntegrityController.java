package com.abdali.microhps.integrityservice.controller;

import static com.abdali.microhps.integrityservice.utils.Constants.DROP_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.MERCHANT_NUMBER_LENGTH;
import static com.abdali.microhps.integrityservice.utils.Constants.REMOVAL_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.VERIFICATION_INDICATOR;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abdali.microhps.integrityservice.exceptions.NoDataFoundException; 
import com.abdali.microhps.integrityservice.proxy.DropMessageProxy;
import com.abdali.microhps.integrityservice.service.TransactionService;
import com.abdali.microhps.integrityservice.validation.MerchantNotFound;
import com.abdali.microhps.integrityservice.validation.MessageFormat;
 

@RestController
public class IntegrityController {

	private MessageFormat messageFormat;
	private MerchantNotFound merchantNotFound;
	private TransactionService transactionService;
	private DropMessageProxy dropMessageProxy;
	
	@Autowired
	public IntegrityController(
			MessageFormat messageFormat,
			MerchantNotFound merchantNotFound,
			TransactionService transactionService,
			DropMessageProxy dropMessageProxy
			) {
		// TODO Auto-generated constructor stub
		this.messageFormat = messageFormat;
		this.merchantNotFound = merchantNotFound;
		this.transactionService = transactionService;
		this.dropMessageProxy = dropMessageProxy;
	}
	
	
	@PostMapping("/check")
	public Boolean testController(@RequestParam("message") String message) {
		Boolean message_format = false;
		
		// Logger logger = LoggerFactory.getLogger(IntegrityController.class);
		
		// read message in separite it
		// remove space
		//	String TrimMessage = message.replaceAll("\\s", "");
		
		// split message into array
		String[] data = message.split(",");
		
		// convert Indicator to char 
		// i don't think i need to make it uppercase if its not uppercase is exception
		//	char indicator = Character.toUpperCase(data[0].charAt(0));
		char indicator = data[0].charAt(0);

		// check if it's drop message -- drop message contain merchantNumber
		//		String Indicator, 
		//		String merchantNumber, 
		//		String Device, 
		//		String Bag, 
		//		String Container, 
		//		String Sequence, 
		//		String TransmitionDate, 
		//		String TransactionId
		

		// register Transaction into audit table. 
//		Transaction transaction = new Transaction(data[0], data[1], data[2], data[3], data[4], data[5], data[8], data[6], message, cars);
//		
//		transactionService.transactionCreate(transaction);
		
		char container;
		
		// -- Check for drop message and verify Merchant Number -- contain just numbers with 15 in length.
		if(indicator == DROP_INDICATOR && data[1].matches("[0-9]+") && data[1].length() == MERCHANT_NUMBER_LENGTH) {  
			container = data[4].charAt(0); 
			// GLOBAL STEPS 1 : INVALID MESSAGE FORMAT .
			if(messageFormat.checkMessageFormat(indicator, data[1], data[2], data[3], container, data[5], data[8], data[6])) {
				// GLOBAL STEPS 2 : MERCHANT NOT FOUND .
//				if(merchantNotFound.checkMerchantNotFound(data[1])) {
//					message_format = true;
//				}
			}
		} else if(indicator == REMOVAL_INDICATOR) {
			container = data[3].charAt(0);
			// don't have just merchant
			message_format = messageFormat.checkMessageFormat(indicator, null, data[1], data[2], container, data[4], data[7], data[5]);
		} else if(indicator == VERIFICATION_INDICATOR) { 
			container = data[3].charAt(0);
			// don't have merchantNumber and sequence
			message_format = messageFormat.checkMessageFormat(indicator, null, data[1], data[2], container, null, data[5], data[4]);
		} else {			
			throw new NoDataFoundException("the Indicator is not valid from Controller " + data[0].charAt(0) + " " + DROP_INDICATOR);
		}
		return message_format;
	}
	
	@GetMapping("/verify/merchant/{merchantNumber}/bag/{bagNumber}/tranasction/{transactionId}/date/{datetime}")
	public Boolean testProxy(@PathVariable("merchantNumber") Long merchantNumber, 
	@PathVariable("bagNumber") String bagNumber, 
	@PathVariable("transactionId") Integer transactionId,
	@PathVariable("datetime") String transmitionDate) {
		return dropMessageProxy.getListDropMessages(merchantNumber, bagNumber, transactionId, transmitionDate);
	}

	
}
