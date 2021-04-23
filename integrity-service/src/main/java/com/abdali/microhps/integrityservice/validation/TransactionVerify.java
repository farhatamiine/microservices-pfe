package com.abdali.microhps.integrityservice.validation;

import static com.abdali.microhps.integrityservice.utils.Constants.MESSAGE_VERIFICATION_CODE;
import static com.abdali.microhps.integrityservice.utils.Constants.MESSAGE_VERIFICATION_DESCRIPTION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.integrityservice.exceptions.IntegrityException;
import com.abdali.microhps.integrityservice.proxy.DropMessageProxy;
import com.abdali.microhps.integrityservice.service.TransactionService;


// - NOTE:: AVAILABLE JUST FOR "VERIFICATION" AND "REMOVAL".
// - Verification message without drops
// o If the bag number do not correspond to any drops in the system, means all device messages are not received

@Service
public class TransactionVerify {
	
	private DropMessageProxy dropMessageProxy;
	private TransactionService transactionService;
	
	@Autowired
	public TransactionVerify(
			DropMessageProxy dropMessageProxy,
			TransactionService transactionService
			) {
		// TODO Auto-generated constructor stub
		this.dropMessageProxy = dropMessageProxy;
		this.transactionService = transactionService;
	}
	
	public Boolean transactionVerification(String bagNumber, char containerType, String[] messageSplited, String message) {
		
		if(dropMessageProxy.isBagNumberHasDrops(bagNumber)) {			
			return true;
		}
		
		transactionService.transactionCreate(containerType, messageSplited, message);
		throw new IntegrityException(MESSAGE_VERIFICATION_CODE, MESSAGE_VERIFICATION_DESCRIPTION);
	}
}
