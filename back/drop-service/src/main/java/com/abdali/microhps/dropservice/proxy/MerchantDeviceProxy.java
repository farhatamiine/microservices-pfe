package com.abdali.microhps.dropservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="device-merchant-service")
public interface MerchantDeviceProxy {
	
	@GetMapping("/merchant-device/{deviceNumber}")
	public Boolean checkDeviceNumber(@PathVariable("deviceNumber") String deviceNumber);
	
}

