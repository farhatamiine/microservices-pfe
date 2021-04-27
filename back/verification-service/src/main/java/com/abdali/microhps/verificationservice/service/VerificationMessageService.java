package com.abdali.microhps.verificationservice.service;

import java.time.Instant;
import java.util.List;

import com.abdali.microhps.verificationservice.dto.VerificationMessageDto;


public interface VerificationMessageService {

	VerificationMessageDto save(VerificationMessageDto verificationMessageDto);

	VerificationMessageDto findById(Long id);
	
	List<VerificationMessageDto> findByDeviceNumber(String deviceNumber);
	
	List<VerificationMessageDto> findByBagNumber(String bagNumber);
	
	List<VerificationMessageDto> findAll();
}
