package com.abdali.microhps.validationexceptionservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="verification-service")
public interface VerificationTransactionProxy {

	@PostMapping("/transactionId/{transactionId}")
	public Boolean isTransactionIdDuplicated(@PathVariable Integer transactionId);
}
