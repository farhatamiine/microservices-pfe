package com.abdali.microhps.integrityservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="device-merchant-service")
public interface MerchantDeviceProxy {
	
	@GetMapping("/merchant-device/status/{merchantNumber}")
	public Boolean isMerchantStatusActive(@PathVariable("merchantNumber") Long merchantNumber);
	
	@GetMapping("/merchant-device/merchant-number/{merchantNumber}/device-number/{deviceNumber}")
	public Boolean isDeviceRelatedToMerchant(@PathVariable("merchantNumber") Long merchantNumber, @PathVariable("deviceNumber") String deviceNumber);
	
//	@GetMapping("/merchant-device/{deviceNumber}")
//	public Boolean checkDeviceNumber(@PathVariable("deviceNumber") String deviceNumber);
}
