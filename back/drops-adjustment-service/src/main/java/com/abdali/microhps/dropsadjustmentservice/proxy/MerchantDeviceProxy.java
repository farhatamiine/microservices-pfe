package com.abdali.microhps.dropsadjustmentservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="device-merchant-service")
public interface MerchantDeviceProxy {
	
	@GetMapping("/merchant-device/merchant-account-credited/{merchantNumber}")
	public String getMerchantCreditedAccount(@PathVariable("merchantNumber") Long merchantNumber);
	
}