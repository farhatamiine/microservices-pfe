package com.abdali.microhps.integrityservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.abdali.microhps.integrityservice.config.feign.FeignSimpleEncoderConfig;

@FeignClient(name="removal-service", configuration = FeignSimpleEncoderConfig.class)
public interface RemovalMessageProxy {

	@PostMapping(value= "/removal-transaction/new")
	public String saveRemovalMessage(@RequestBody String message);
}

