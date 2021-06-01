package com.abdali.microhps.verificationadjustmentservice.proxy;
 
import java.time.Instant;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.abdali.microhps.verificationadjustmentservice.model.CoreTransactionModel;

@FeignClient(name="removal-service")
public interface RemovalTransactionProxy {
	
	@GetMapping("/removal-transaction/device/{deviceNumber}/bag/{bagNumber}/startDate/{startDate}/endDate/{endDate}")
	public CoreTransactionModel removalMessageBetwwenDates(
			@PathVariable("deviceNumber") String deviceNumber, 
			@PathVariable("bagNumber") String bagNumber,
			@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate);
	
	@GetMapping("/removal-transaction/last-removal-date/device/{deviceNumber}/bag/{bagNumber}")
	public Instant getDatefromLastRemoval(
			@PathVariable("deviceNumber") String deviceNumber, 
			@PathVariable("bagNumber") String bagNumber);
	
}