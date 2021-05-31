package com.abdali.microhps.removaladjustmentservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="adjustment-events-service")
public interface AdjustmentEventProxy {

	@PostMapping(value= "/adjustment-event/new-adjustment")
	public String saveVerificationMessage(@RequestBody String message);
	
}
