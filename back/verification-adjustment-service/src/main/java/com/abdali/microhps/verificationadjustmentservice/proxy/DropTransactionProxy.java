package com.abdali.microhps.verificationadjustmentservice.proxy;

import java.time.Instant;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.abdali.microhps.verificationadjustmentservice.model.CoreTransactionModel;
 
@FeignClient(name="drop-service")
public interface DropTransactionProxy {
	
	@GetMapping("/drop-transaction/device/{deviceNumber}/bag/{bagNumber}/startDate/{startDate}/endDate/{endDate}")
	public List<CoreTransactionModel> listDropsBetwwenDates(
			@PathVariable("deviceNumber") String deviceNumber, 
			@PathVariable("bagNumber") String bagNumber,
			@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate);
	
	@GetMapping("/drop-transaction/last-drop-date/device/{deviceNumber}/bag/{bagNumber}/startDate/{startDate}/endDate/{endDate}")
	public Instant getDatefromFirstDrop( 
			@PathVariable("deviceNumber") String deviceNumber, 
			@PathVariable("bagNumber") String bagNumber,
			@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate);
}
