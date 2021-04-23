package com.abdali.microhps.integrityservice.validation;
 
import static com.abdali.microhps.integrityservice.utils.Constants.MESSAGE_MERCHANT_NOT_FOUND_CODE;
import static com.abdali.microhps.integrityservice.utils.Constants.MESSAGE_MERCHANT_NOT_FOUND_DESCRIPTION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.integrityservice.exceptions.IntegrityException;
import com.abdali.microhps.integrityservice.proxy.MerchantDeviceProxy;
import com.abdali.microhps.integrityservice.service.TransactionService;
 
// - NOTE:: AVAILABLE JUST FOR "DROPS" OTHERS DIDN'T HAVE MERCHANT NUMBER.
// - Merchant not found: this validation fails when the merchant number meet any of the following conditions
//o Not found in PowerCARD outlet table.
//o Outlet status is closed.
//o Outlet status is suspended.
//o Outlet status is deactivated.

@Service
public class MerchantNotFound {
	
	private MerchantDeviceProxy merchantDeviceProxy;
	private TransactionService transactionService;
	
	@Autowired
	public MerchantNotFound(MerchantDeviceProxy merchantDeviceProxy, TransactionService transactionService) {
		this.merchantDeviceProxy = merchantDeviceProxy;
		this.transactionService = transactionService;
	}

	public Boolean checkMerchantNotFound(Long merchant, char containerType, String[] messageSplited, String message) {
		
		Boolean merchantStatus = merchantDeviceProxy.checkMerchantState(merchant);
		
		if(merchantStatus) {
			return true;
		} else {			
			transactionService.transactionCreate(containerType, messageSplited, message);
			throw new IntegrityException(MESSAGE_MERCHANT_NOT_FOUND_CODE, MESSAGE_MERCHANT_NOT_FOUND_DESCRIPTION);
		}

	}

}
