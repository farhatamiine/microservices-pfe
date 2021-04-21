package com.abdali.microhps.integrityservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.abdali.microhps.integrityservice.config.feign.FeignSimpleEncoderConfig;


@FeignClient(name="verification-service", configuration = FeignSimpleEncoderConfig.class)
public interface VerificationMessageProxy {

	@PostMapping(value= "/verification-message/new")
	public String saveVerificationMessage(@RequestBody String message);
	
}
