package com.abdali.microhps.integrityservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.abdali.microhps.integrityservice.config.feign.FeignSimpleEncoderConfig;


@FeignClient(name="drop-service", configuration = FeignSimpleEncoderConfig.class)
public interface DropMessageProxy {
	
	@GetMapping("/dropmessage/merchant/{merchantNumber}/bag/{bagNumber}/tranasction/{transactionId}/date/{datetime}")
	public Boolean getListDropMessages(
			@PathVariable("merchantNumber") Long merchantNumber, 
			@PathVariable("bagNumber") String bagNumber, 
			@PathVariable("transactionId") Integer transactionId,
			@PathVariable("datetime") String transmitionDate);
	
	
	@GetMapping("/dropmessage/verify/bag/{bagNumber}")
	public Boolean verifyTransactionForIntegrityBagNumber(@PathVariable("bagNumber") String bagNumber);
	
	@PostMapping(value= "/dropmessage/new")
	public String saveDropMessage(@RequestBody String message);
}
