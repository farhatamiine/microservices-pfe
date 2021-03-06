package com.abdali.microhps.integrityservice.validation;

import static com.abdali.microhps.integrityservice.utils.Constants.MESSAGE_DUPLICTAED_CODE;
import static com.abdali.microhps.integrityservice.utils.Constants.MESSAGE_DUPLICTAED_DESCRIPTION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.integrityservice.exceptions.IntegrityException;
import com.abdali.microhps.integrityservice.model.Transaction;
import com.abdali.microhps.integrityservice.proxy.DropMessageProxy;
import com.abdali.microhps.integrityservice.service.TransactionBuilder;
import com.abdali.microhps.integrityservice.service.TransactionService; 

//- Duplicated message: this message fails when an existing message is found with the following
//o Same merchant number.
//o Same bag number.
//o Same transaction id.
//o Same date and time.

@Service
public class DuplicatedMessage {

	private DropMessageProxy dropMessageProxy;
	private TransactionService transactionService;
	private TransactionBuilder transactionBuilder;
	
	@Autowired
	public DuplicatedMessage(
			DropMessageProxy dropMessageProxy,
			TransactionService transactionService,
			TransactionBuilder transactionBuilder
			) {
		// TODO Auto-generated constructor stub
		this.dropMessageProxy = dropMessageProxy;
		this.transactionService = transactionService;
		this.transactionBuilder = transactionBuilder;
	}
	
	public Boolean checkForDuplicatedMessage(
			Long merchantNumber, 
			String bagNumber, 
			String transmitionDate, 
			Integer transactionId,
			char containerType,
			String[] messageSplited, 
			String message) {

		Boolean isDropMessageExist = dropMessageProxy.isMessageExist(merchantNumber, bagNumber, transactionId, transmitionDate);
		
		if(isDropMessageExist) {	
			Transaction transaction = transactionBuilder.transactionBuild(containerType, messageSplited, message);
			transactionService.transactionCreate(transaction);
			throw new IntegrityException(MESSAGE_DUPLICTAED_CODE, MESSAGE_DUPLICTAED_DESCRIPTION);
		} else {			
			return true;
		}

	}

	
	
}
