package com.abdali.microhps.verificationservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.abdali.microhps.verificationservice.dto.VerificationCoreTransactionDto;
import com.abdali.microhps.verificationservice.service.VerificationMessageService;

@RestController
public class VerificationController {
	
	VerificationMessageService verificationMessageService;
	
	@Autowired
	public VerificationController(
			VerificationMessageService verificationMessageService
			) {
		this.verificationMessageService = verificationMessageService;
	}

	@GetMapping("/verification-transaction")
	public List<VerificationCoreTransactionDto> getMessages() {
		return verificationMessageService.findAll();
	}
	
	@GetMapping("/verification-transaction/{messageId}")
	public VerificationCoreTransactionDto getMessageById(@PathVariable("messageId") Long Id) {
		return verificationMessageService.findById(Id);
	}

	@GetMapping("/verification-transaction/device/{deviceNumber}")
	public List<VerificationCoreTransactionDto> getMessageByDeviceNumber(@PathVariable("deviceNumber") String deviceNumber) {
		return verificationMessageService.findByDeviceNumber(deviceNumber);
	}

	@GetMapping("/verification-transaction/bag/{bagNumber}")
	public List<VerificationCoreTransactionDto> getMessageByBagNumber(@PathVariable("bagNumber") String bagNumber) {
		return verificationMessageService.findByBagNumber(bagNumber);
	}
	
	@GetMapping("/verification-transaction/verify/bag/{bagNumber}")
	public Boolean verifyTransactionForIntegrityBagNumber(@PathVariable("bagNumber") String bagNumber) {
		if(verificationMessageService.findByBagNumber(bagNumber).isEmpty()) {
			return false;
		}
		return true;
	}
}
