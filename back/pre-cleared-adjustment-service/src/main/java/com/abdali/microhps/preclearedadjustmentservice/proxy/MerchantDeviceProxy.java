package com.abdali.microhps.preclearedadjustmentservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="device-merchant-service")
public interface MerchantDeviceProxy {
	
	@GetMapping("/merchant-account/{merchantNumber}/account-type/{type}")
	public String getMerchantCreditedAccount(@PathVariable("merchantNumber") Long merchantNumber,@PathVariable("type") String type);
	
}