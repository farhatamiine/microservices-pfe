package com.abdali.microhps.validationexceptionservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="drop-service")
public interface DropTransactionProxy {
	
	@PostMapping("/drop-transaction/transactionId/{transactionId}")
	public Boolean isTransactionIdDuplicated(@PathVariable Integer transactionId);
	
}
