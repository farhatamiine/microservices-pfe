package com.abdali.microhps.verificationadjustmentservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.abdali.microhps.verificationadjustmentservice.model.CoreTransactionModel;

@FeignClient(name="removal-service")
public interface RemovalTransactionProxy {
	@GetMapping("/removal-transaction/device/{deviceNumber}/bag/{bagNumber}/transaction/{transactionId}")
	public CoreTransactionModel findRemovalTransaction(
			@PathVariable("deviceNumber") String deviceNumber, 
			@PathVariable("bagNumber") String bagNumber,
			@PathVariable("transactionId") Integer transactionId
			);
}