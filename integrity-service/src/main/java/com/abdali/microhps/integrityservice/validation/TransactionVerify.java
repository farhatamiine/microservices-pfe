package com.abdali.microhps.integrityservice.validation;

import static com.abdali.microhps.integrityservice.utils.Constants.MESSAGE_VERIFICATION_CODE;
import static com.abdali.microhps.integrityservice.utils.Constants.MESSAGE_VERIFICATION_DESCRIPTION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.integrityservice.exceptions.IntegrityException;
import com.abdali.microhps.integrityservice.proxy.DropMessageProxy;

//- Verification message without drops
//o If the bag number do not correspond to any drops in the system, means all device messages are not received
//------------ this will be checked for only verification and removed messages i think. ---------------------


@Service
public class TransactionVerify {
	
	private DropMessageProxy dropMessageProxy;
	
	@Autowired
	public TransactionVerify(
			DropMessageProxy dropMessageProxy
			) {
		// TODO Auto-generated constructor stub
		this.dropMessageProxy = dropMessageProxy;
	}
	
	
	public Boolean transactionVerification(String bagNumber) {
		if(dropMessageProxy.verifyTransactionForIntegrityBagNumber(bagNumber)) {			
			return true;
		}
		throw new IntegrityException(MESSAGE_VERIFICATION_CODE, MESSAGE_VERIFICATION_DESCRIPTION);
	}
}
