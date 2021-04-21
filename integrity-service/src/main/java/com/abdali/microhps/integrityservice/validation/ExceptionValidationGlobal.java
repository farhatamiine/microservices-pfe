package com.abdali.microhps.integrityservice.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.integrityservice.proxy.MerchantDeviceProxy;


// Device Number Missing : If a device number does not exist on PowerCARD. PowerCARD should raise an exception.
// Transaction ID : If Transaction ID is duplicated PowerCARD should raise an exception.
// Container Type : If drop message contain Notes & coins PowerCARD should raise an exception.
// Denomination 1 to 6: Values should be set to “0” by default if there is no coin deposit.
// Denomination 7 to 11: Values should be set to “0” by default if there is no notes deposit.
// Denominations 12 to 15: Values should be set to “0” by default. 



@Service
public class ExceptionValidationGlobal {
	
	private MerchantDeviceProxy merchantDeviceProxy;
	
	@Autowired
	public ExceptionValidationGlobal(MerchantDeviceProxy merchantDeviceProxy) {
		this.merchantDeviceProxy = merchantDeviceProxy;
	}
	
	public boolean exceptionvalidation(
			String deviceNumber,
			String transactionId
			) {
		
		
		
		return true;
	}
}
