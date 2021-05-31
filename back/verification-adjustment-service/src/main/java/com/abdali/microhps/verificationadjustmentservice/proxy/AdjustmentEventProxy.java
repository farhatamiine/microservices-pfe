package com.abdali.microhps.verificationadjustmentservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.abdali.microhps.verificationadjustmentservice.model.AdjustmentEvent;

@FeignClient(name="adjustment-events-service")
public interface AdjustmentEventProxy {

	@PostMapping(value= "/adjustment-event/new-adjustment")
	public String saveVerificationMessage(@RequestBody String message);
 
	@GetMapping("/adjustment-event/get-adjustment/merchant-number/{merchantNumber}/fromDate/{date}")
	public AdjustmentEvent getAdjustment(
			@PathVariable("merchantNumber") Long merchantNumber,
			@PathVariable("date") String fromDate
	);
}