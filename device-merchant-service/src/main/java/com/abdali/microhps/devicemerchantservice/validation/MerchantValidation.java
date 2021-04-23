package com.abdali.microhps.devicemerchantservice.validation;

import static com.abdali.microhps.devicemerchantservice.utils.Constants.MERCHANT_NUMBER_LENGTH;

import java.util.ArrayList;
import java.util.List;
import com.abdali.microhps.devicemerchantservice.dto.MerchantDto;

public class MerchantValidation {
	
	public static List<String> validate(MerchantDto merchantDto) {
	    List<String> errors = new ArrayList<>();
		// check for Merchant Number 
	    if(merchantDto.getMerchantNumber() == null) {
	    	errors.add("missing arguments");
	    } 
	    if(Long.toString(merchantDto.getMerchantNumber()).length() != MERCHANT_NUMBER_LENGTH) {
	    	errors.add("merchant number is not valid. length = " + MERCHANT_NUMBER_LENGTH);
	    } 
//	    else if(powercardMerchant.getDevice().length() != DEVICE_NUMBER) {
//	    	errors.add("device number is not valid. length = " + DEVICE_NUMBER);
//	    }
	    return errors;
	  }
}
