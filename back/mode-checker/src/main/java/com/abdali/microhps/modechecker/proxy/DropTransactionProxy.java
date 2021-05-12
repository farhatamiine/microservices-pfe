package com.abdali.microhps.modechecker.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="drop-service")
public interface DropTransactionProxy {

	@GetMapping("/drop-transaction/device/{deviceNumber}/bag/{bagNumber}")
	public Long getMerchantNumber(
			@PathVariable("deviceNumber") String deviceNumber,
			@PathVariable("bagNumber") String bagNumber
			);
}
