package com.abdali.microhps.dropservice.powercard.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.dropservice.proxy.MerchantDeviceProxy;


// Device Number Missing : If a device number does not exist on PowerCARD. PowerCARD should raise an exception.
// Transaction ID : If Transaction ID is duplicated PowerCARD should raise an exception.
// Container Type : If drop message contain Notes & coins PowerCARD should raise an exception.
// Denomination 1 to 6: Values should be set to “0” by default if there is no coin deposit.
// Denomination 7 to 11: Values should be set to “0” by default if there is no notes deposit.
// Denominations 12 to 15: Values should be set to “0” by default. 
//	Merchant number missing (merchant number does not exist on PowerCard).
//	If Device is not linked to merchant.
//	If Sequence number does not exist on.
//	If sequence number is duplicated.



@Service
public class PowerCardDropMessageExceptions {
	
	private MerchantDeviceProxy merchantDeviceProxy;
	
	@Autowired
	public PowerCardDropMessageExceptions(MerchantDeviceProxy merchantDeviceProxy) {
		this.merchantDeviceProxy = merchantDeviceProxy;
	}
	
	public List<String> exceptionvalidation(
			String deviceNumber,
			String transactionId
			) {		
		List<String> errors = new ArrayList<>();
		
		// TODO : check for Device NUmber 
	    if(!merchantDeviceProxy.checkDeviceNumber(deviceNumber)) {
	    	errors.add("device Number is missing on powerCard Table");
	    } 
		    
		return errors;
	}
}
