package com.abdali.microhps.modechecker.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="device-merchant-service")
public interface DeviceMerchantProxy {

	@GetMapping("/merchant-device/settlement-mode/{merchantNumber}")
	public String returnMerchantSettlementMode(@PathVariable("merchantNumber") Long merchantNumber);
	
}
