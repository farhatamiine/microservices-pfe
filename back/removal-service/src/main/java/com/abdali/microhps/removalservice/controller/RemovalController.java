package com.abdali.microhps.removalservice.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.abdali.microhps.removalservice.dto.RemovalCoreMessageDto;
import com.abdali.microhps.removalservice.model.RemovalCoreMessage;
import com.abdali.microhps.removalservice.service.RemovalMessageService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@RestController
@Slf4j
@RequestMapping("/removal-transaction")
public class RemovalController {
	
	RemovalMessageService removalMessageService;
	
	@Autowired
	public RemovalController(
			RemovalMessageService removalMessageService
			) {
		this.removalMessageService = removalMessageService;
	}

	@GetMapping("/")
	public List<RemovalCoreMessageDto> getMessages() {
		return removalMessageService.findAll();
	}
	
	@GetMapping("/{messageId}")
	public RemovalCoreMessageDto getMessageById(@PathVariable("messageId") Long Id) {
		return removalMessageService.findById(Id);
	}

	@GetMapping("/device/{deviceNumber}")
	public List<RemovalCoreMessageDto> getMessageByDeviceNumber(@PathVariable("deviceNumber") String deviceNumber) {
		return removalMessageService.findByDeviceNumber(deviceNumber);
	}

	@GetMapping("/bag/{bagNumber}")
	public List<RemovalCoreMessageDto> getMessageByBagNumber(@PathVariable("bagNumber") String bagNumber) {
		return removalMessageService.findByBagNumber(bagNumber);
	}
	

	/*
	 * Duplicated Transaction.
	 * @param TransactionId
	 * @return true if its depilcated, false if not.
	 */
	@PostMapping("/transactionId/{transactionId}")
	public Boolean isTransactionIdDuplicated(@PathVariable Integer transactionId) {
		return removalMessageService.findByTransactionId(transactionId);
	}
	
	@GetMapping("/device/{deviceNumber}/bag/{bagNumber}/transaction/{transactionId}")
	public RemovalCoreMessage findRemovalTransaction(
			@PathVariable("deviceNumber") String deviceNumber, 
			@PathVariable("bagNumber") String bagNumber,
			@PathVariable("transactionId") Integer transactionId) {
		return removalMessageService.findRemovalTransaction(deviceNumber, bagNumber, transactionId);
	}
	
	@GetMapping("/device/{deviceNumber}/bag/{bagNumber}/startDate/{startDate}/endDate/{endDate}")
	public RemovalCoreMessage listDropsBetwwenDates(
			@PathVariable("deviceNumber") String deviceNumber, 
			@PathVariable("bagNumber") String bagNumber,
			@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate) throws Exception {
		startDate = URLDecoder.decode(startDate, StandardCharsets.UTF_8);
		endDate = URLDecoder.decode(endDate, StandardCharsets.UTF_8);
		
		final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
                .withZone(ZoneId.systemDefault());
		
		Instant startDateInstant = Instant.from(formatter.parse(startDate));
		Instant endDateInstant = Instant.from(formatter.parse(endDate));
//		throw new Exception(startDateInstant +"______________________________" + endDateInstant );
//		
		return removalMessageService.removalBetwwenDates(deviceNumber, bagNumber, startDateInstant, endDateInstant);
	}
	
	@GetMapping("/last-removal-date/device/{deviceNumber}/bag/{bagNumber}")
	public Instant getDatefromLastRemoval(
			@PathVariable("deviceNumber") String deviceNumber, 
			@PathVariable("bagNumber") String bagNumber) {
		return removalMessageService.findLastOneBydeviceAndBag(deviceNumber, bagNumber);
	}
}
