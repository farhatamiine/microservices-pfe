package com.abdali.microhps.integrityservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.abdali.microhps.integrityservice.config.feign.FeignSimpleEncoderConfig;

@FeignClient(name="drop-service", configuration = FeignSimpleEncoderConfig.class)
public interface DropMessageProxy {
	
	@GetMapping("/drop-transaction/merchant/{merchantNumber}/bag/{bagNumber}/tranasction/{transactionId}/date/{datetime}")
	public Boolean isMessageExist(
			@PathVariable("merchantNumber") Long merchantNumber, 
			@PathVariable("bagNumber") String bagNumber, 
			@PathVariable("transactionId") Integer transactionId,
			@PathVariable("datetime") String transmitionDate);
	
	
	@GetMapping("/drop-transaction/verify/bag/{bagNumber}")
	public Boolean isBagNumberHasDrops(@PathVariable("bagNumber") String bagNumber);
	
//	@PostMapping(value= "/drop-transaction/new")
//	public String saveDropMessage(@RequestBody String message);
	
}
