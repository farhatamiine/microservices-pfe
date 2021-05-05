package com.abdali.microhps.validationexceptionservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="removal-service")
public interface RemovalTransactionProxy {

	@PostMapping("/transactionId/{transactionId}")
	public Boolean isTransactionIdDuplicated(@PathVariable Integer transactionId);
	
}
