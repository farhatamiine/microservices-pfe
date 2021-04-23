package com.abdali.microhps.devicemerchantservice.validation;

import java.util.ArrayList;
import java.util.List;
import com.abdali.microhps.devicemerchantservice.dto.DeviceDto;

public class DeviceValidation {
	
	public static List<String> validate(DeviceDto deviceDto) {
	    List<String> errors = new ArrayList<>();
		// TODO : check for Device Number.
	    if(deviceDto.getDeviceNumber() == null) {
	    	errors.add("missing arguments");
	    } 
	    return errors;
	  }
}
