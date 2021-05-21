package com.abdali.microhps.verificationadjustmentservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="device-merchant-service")
public interface MerchantDeviceProxy {
	
	@GetMapping("/account-limits/merchant-number/{merchantNumber}")
	public String getMerchantLimits(@PathVariable("merchantNumber") Long merchantNumber);
	
}