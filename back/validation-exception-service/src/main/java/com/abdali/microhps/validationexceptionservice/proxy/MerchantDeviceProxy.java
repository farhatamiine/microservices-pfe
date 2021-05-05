package com.abdali.microhps.validationexceptionservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="device-merchant-service")
public interface MerchantDeviceProxy {
	@GetMapping("/merchant-device/device-exist/{deviceNumber}")
	public Boolean isDeviceNumberExist(@PathVariable("deviceNumber") String deviceNumber);
}