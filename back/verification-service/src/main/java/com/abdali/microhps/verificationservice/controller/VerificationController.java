package com.abdali.microhps.verificationservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdali.microhps.verificationservice.dto.VerificationCoreTransactionDto;
import com.abdali.microhps.verificationservice.service.VerificationMessageService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@RestController
@Slf4j
@RequestMapping("/verification-transaction")
public class VerificationController {
	
	VerificationMessageService verificationMessageService;
	
	@Autowired
	public VerificationController(
			VerificationMessageService verificationMessageService
			) {
		this.verificationMessageService = verificationMessageService;
	}

	@GetMapping("/")
	public List<VerificationCoreTransactionDto> getMessages() {
		return verificationMessageService.findAll();
	}
	
	@GetMapping("/{messageId}")
	public VerificationCoreTransactionDto getMessageById(@PathVariable("messageId") Long Id) {
		return verificationMessageService.findById(Id);
	}

	@GetMapping("/device/{deviceNumber}")
	public List<VerificationCoreTransactionDto> getMessageByDeviceNumber(@PathVariable("deviceNumber") String deviceNumber) {
		return verificationMessageService.findByDeviceNumber(deviceNumber);
	}

	@GetMapping("/bag/{bagNumber}")
	public List<VerificationCoreTransactionDto> getMessageByBagNumber(@PathVariable("bagNumber") String bagNumber) {
		return verificationMessageService.findByBagNumber(bagNumber);
	}
	
	@GetMapping("/verify/bag/{bagNumber}")
	public Boolean verifyTransactionForIntegrityBagNumber(@PathVariable("bagNumber") String bagNumber) {
		if(verificationMessageService.findByBagNumber(bagNumber).isEmpty()) {
			return false;
		}
		return true;
	}

	/*
	 * Duplicated Transaction.
	 * @param TransactionId
	 * @return true if its depilcated, false if not.
	 */
	@PostMapping("/transactionId/{transactionId}")
	public Boolean isTransactionIdDuplicated(@PathVariable Integer transactionId) {
		return verificationMessageService.findByTransactionId(transactionId);
	}
}
