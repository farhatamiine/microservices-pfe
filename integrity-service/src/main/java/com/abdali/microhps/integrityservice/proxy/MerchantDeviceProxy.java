package com.abdali.microhps.integrityservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="device-merchant-service")
public interface MerchantDeviceProxy {
	
	@GetMapping("/merchant-device/status/{merchantNumber}")
	public Boolean checkMerchantState(@PathVariable("merchantNumber") Long merchantNumber);
	
	@GetMapping("/merchants/merchant-number/{merchantNumber}/device-number/{deviceNumber}")
	public Boolean relationMerchantDevice(@PathVariable("merchantNumber") Long merchantNumber, @PathVariable("deviceNumber") String deviceNumber);
}
