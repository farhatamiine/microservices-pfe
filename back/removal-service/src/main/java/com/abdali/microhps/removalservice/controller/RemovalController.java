package com.abdali.microhps.removalservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.abdali.microhps.removalservice.dto.RemovalCoreMessageDto;
import com.abdali.microhps.removalservice.service.RemovalMessageService;

@RestController
public class RemovalController {
	
	RemovalMessageService removalMessageService;
	
	@Autowired
	public RemovalController(
			RemovalMessageService removalMessageService
			) {
		this.removalMessageService = removalMessageService;
	}

	@GetMapping("/removalmessage")
	public List<RemovalCoreMessageDto> getMessages() {
		return removalMessageService.findAll();
	}
	
	@GetMapping("/removalmessage/{messageId}")
	public RemovalCoreMessageDto getMessageById(@PathVariable("messageId") Long Id) {
		return removalMessageService.findById(Id);
	}

	@GetMapping("/removalmessage/device/{deviceNumber}")
	public List<RemovalCoreMessageDto> getMessageByDeviceNumber(@PathVariable("deviceNumber") String deviceNumber) {
		return removalMessageService.findByDeviceNumber(deviceNumber);
	}

	@GetMapping("/removalmessage/bag/{bagNumber}")
	public List<RemovalCoreMessageDto> getMessageByBagNumber(@PathVariable("bagNumber") String bagNumber) {
		return removalMessageService.findByBagNumber(bagNumber);
	}
}
