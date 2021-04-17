package com.abdali.microhps.integrityservice.validation;

import static com.abdali.microhps.integrityservice.utils.Constants.MESSAGE_MERCHANT_NOT_MAPPED_CODE;
import static com.abdali.microhps.integrityservice.utils.Constants.MESSAGE_MERCHANT_NOT_MAPPED_DESCRIPTION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.integrityservice.exceptions.IntegrityException;
import com.abdali.microhps.integrityservice.proxy.DropMessageProxy;
import com.abdali.microhps.integrityservice.proxy.MerchantDeviceProxy;

//- Device not mapped: this validation fails in 2 different cases
//o Simple merchant: device do not belong to the merchant in the message.
//o Deposit merchant: deposit outlet and device not linked to the same sponsor outlet. ????????????????

@Service
public class MerchantNotMapped {
	
	private MerchantDeviceProxy merchantDeviceProxy;
	
	@Autowired
	public MerchantNotMapped(
			MerchantDeviceProxy merchantDeviceProxy
			) {
		this.merchantDeviceProxy= merchantDeviceProxy;
	}
	
	
	public Boolean simpleMerchant(Long merchantNumber, String deviceNumber) {
		if(merchantDeviceProxy.relationMerchantDevice(merchantNumber, deviceNumber)) {
			return true;
		}
		throw new IntegrityException(MESSAGE_MERCHANT_NOT_MAPPED_CODE, MESSAGE_MERCHANT_NOT_MAPPED_DESCRIPTION);
	}

}
