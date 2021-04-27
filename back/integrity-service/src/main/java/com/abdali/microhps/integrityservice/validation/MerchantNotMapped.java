package com.abdali.microhps.integrityservice.validation;

import static com.abdali.microhps.integrityservice.utils.Constants.MESSAGE_MERCHANT_NOT_MAPPED_CODE;
import static com.abdali.microhps.integrityservice.utils.Constants.MESSAGE_MERCHANT_NOT_MAPPED_DESCRIPTION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.integrityservice.exceptions.IntegrityException;
import com.abdali.microhps.integrityservice.model.Transaction;
import com.abdali.microhps.integrityservice.proxy.MerchantDeviceProxy;
import com.abdali.microhps.integrityservice.service.TransactionBuilder;
import com.abdali.microhps.integrityservice.service.TransactionService;

// - NOTE:: AVAILABLE JUST FOR "DROPS" OTHERS DIDN'T HAVE MERCHANT NUMBER.
//- Device not mapped: this validation fails in 2 different cases
//o Simple merchant: device do not belong to the merchant in the message.
//o Deposit merchant: deposit outlet and device not linked to the same sponsor outlet. // TODO: LATER MISSING CONCEPT.

@Service
public class MerchantNotMapped {
	
	private MerchantDeviceProxy merchantDeviceProxy;
	private TransactionService transactionService;
	private TransactionBuilder transactionBuilder;
	
	@Autowired
	public MerchantNotMapped(
			MerchantDeviceProxy merchantDeviceProxy,
			TransactionService transactionService,
			TransactionBuilder transactionBuilder
			) {
		this.merchantDeviceProxy= merchantDeviceProxy;
		this.transactionService = transactionService;
		this.transactionBuilder = transactionBuilder;
	}
	
	
	public Boolean simpleMerchant(Long merchantNumber, String deviceNumber, char containerType, String[] messageSplited, String message) {
		if(merchantDeviceProxy.isDeviceRelatedToMerchant(merchantNumber, deviceNumber)) {
			return true;
		}
		Transaction transaction = transactionBuilder.transactionBuild(containerType, messageSplited, message);
		transactionService.transactionCreate(transaction);
		throw new IntegrityException(MESSAGE_MERCHANT_NOT_MAPPED_CODE, MESSAGE_MERCHANT_NOT_MAPPED_DESCRIPTION);
	}

}
