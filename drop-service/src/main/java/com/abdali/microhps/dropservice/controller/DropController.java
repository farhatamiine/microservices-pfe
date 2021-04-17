package com.abdali.microhps.dropservice.controller;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abdali.microhps.dropservice.dto.DropMessageDto;
import com.abdali.microhps.dropservice.service.DropMessageService;

@RestController
public class DropController {
	
	DropMessageService dropMessageService;
	
	@Autowired
	public DropController(
			DropMessageService dropMessageService
			) {
		this.dropMessageService = dropMessageService;
	}

	@PostMapping("/dropmessage/new")
	public DropMessageDto addMessage(@RequestBody DropMessageDto dropMessageDto) {
		return dropMessageService.save(dropMessageDto);
	}
	
	@GetMapping("/dropmessage")
	public List<DropMessageDto> getMessages() {
		return dropMessageService.findAll();
	}
	
	@GetMapping("/dropmessage/{messageId}")
	public DropMessageDto getMessageById(@PathVariable("messageId") Long Id) {
		return dropMessageService.findById(Id);
	}
	
	@GetMapping("/dropmessage/merchant/{merchantNumber}")
	public List<DropMessageDto> getMessageByMerchantNumber(@PathVariable("merchantNumber") Long Id) {
		return dropMessageService.findByMerchantNumber(Id);
	}
	
	@GetMapping("/dropmessage/device/{deviceNumber}")
	public List<DropMessageDto> getMessageByDeviceNumber(@PathVariable("deviceNumber") String Id) {
		return dropMessageService.findByDeviceNumber(Id);
	}

	@GetMapping("/dropmessage/bag/{bagNumber}")
	public List<DropMessageDto> getMessageByBagNumber(@PathVariable("bagNumber") String Id) {
		return dropMessageService.findByBagNumber(Id);
	}
	
	@GetMapping("/dropmessage/bag/{bagNumber}")
	public Boolean verifyTransactionForIntegrityBagNumber(@PathVariable("bagNumber") String Id) {
		if(dropMessageService.findByBagNumber(Id).isEmpty()) {
			return false;
		}
		return true;
	}
	
	@GetMapping("dropmessage/merchant/{merchantNumber}/bag/{bagNumber}/tranasction/{transactionId}/date/{datetime}")
	public Boolean verifyMessage(
			@PathVariable("merchantNumber") Long merchantNumber, 
			@PathVariable("bagNumber") String bagNumber, 
			@PathVariable("transactionId") Integer transactionId,
			@PathVariable("datetime") String transmitionDate) {

		final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
                .withZone(ZoneId.systemDefault());
		Instant transmitionInstant = Instant.from(formatter.parse(transmitionDate));
		
		if(dropMessageService.findByMerchantNumberAndBagNumberAndTransactionIdAndTransmitionDate(merchantNumber, bagNumber, transactionId, transmitionInstant).isEmpty()) {
			return false;
		}
		return true;
	}
}
