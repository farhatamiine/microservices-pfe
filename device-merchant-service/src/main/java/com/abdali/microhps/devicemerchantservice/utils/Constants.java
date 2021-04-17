package com.abdali.microhps.devicemerchantservice.utils;

import java.util.Arrays;
import java.util.List;

public interface Constants {
	
	// List of Outlet to check for it -- MerchantNotFound Service Validation.
	List<String> POWERCARD_MERCHANT_STATUS = Arrays.asList("closed", "suspended", "deactivated");
	
	int MERCHANT_NUMBER_LENGTH = 15;
}
