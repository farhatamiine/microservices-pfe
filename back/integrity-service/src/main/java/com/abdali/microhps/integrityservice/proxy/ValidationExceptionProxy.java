package com.abdali.microhps.integrityservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.abdali.microhps.integrityservice.config.feign.FeignSimpleEncoderConfig;

@FeignClient(name="exception-validation-service", configuration = FeignSimpleEncoderConfig.class)
public interface ValidationExceptionProxy {

	@PostMapping("/exception-validation/indicator/{indicator}/sequenceNumber/{sequenceNumber}/bag/{bagNumber}/tranasction/{transactionId}/device/{deviceNumber}")
	public Boolean isPowerCardConditionValid(
			@PathVariable("indicator") char indicator,
			@PathVariable("sequenceNumber") int sequenceNumber, 
			@PathVariable("bagNumber") String bagNumber, 
			@PathVariable("transactionId") String transactionId,
			@PathVariable("deviceNumber") String deviceNumber);
}