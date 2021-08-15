package com.abdali.microhps.verificationadjustmentservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
 
import com.abdali.microhps.verificationadjustmentservice.model.AccountLimitsModel;

@FeignClient(name="device-merchant-service")
public interface MerchantDeviceProxy {
	
	@GetMapping("/account-limits/merchant-number/{merchantNumber}")
	public AccountLimitsModel getMerchantLimits(@PathVariable("merchantNumber") Long merchantNumber);
	
	@GetMapping("/merchant-account/{merchantNumber}/account-type/{type}")
	public String getMerchantAccount(@PathVariable("merchantNumber") Long merchantNumber,@PathVariable("type") String type);
	
}