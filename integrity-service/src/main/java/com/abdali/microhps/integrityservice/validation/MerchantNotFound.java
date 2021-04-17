package com.abdali.microhps.integrityservice.validation;
 
import static com.abdali.microhps.integrityservice.utils.Constants.MESSAGE_MERCHANT_NOT_FOUND_CODE;
import static com.abdali.microhps.integrityservice.utils.Constants.MESSAGE_MERCHANT_NOT_FOUND_DESCRIPTION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.integrityservice.exceptions.IntegrityException;
import com.abdali.microhps.integrityservice.proxy.MerchantDeviceProxy;
 

//- Merchant not found: this validation fails when the merchant number meet any of the following conditions
//o Not found in PowerCARD outlet table.
//o Outlet status is closed.
//o Outlet status is suspended.
//o Outlet status is deactivated.

@Service
public class MerchantNotFound {
	
	private MerchantDeviceProxy merchantDeviceProxy;
	
	@Autowired
	public MerchantNotFound(MerchantDeviceProxy merchantDeviceProxy) {
		this.merchantDeviceProxy = merchantDeviceProxy;
	}

	public Boolean checkMerchantNotFound(Long merchant) {
		Boolean merchantStatus = merchantDeviceProxy.checkMerchantState(merchant);
		
		if(merchantStatus) {
			return true;
		}
		throw new IntegrityException(MESSAGE_MERCHANT_NOT_FOUND_CODE, MESSAGE_MERCHANT_NOT_FOUND_DESCRIPTION);
	}

}
