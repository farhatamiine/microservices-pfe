package com.abdali.microhps.verificationservice.service.impl;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.verificationservice.dto.VerificationMessageDto;
import com.abdali.microhps.verificationservice.exceptions.types.NoDataFoundException;
import com.abdali.microhps.verificationservice.repository.VerificationRepository;
import com.abdali.microhps.verificationservice.service.VerificationMessageService;


@Service
public class VerificationMessageServiceImpl implements VerificationMessageService {

	VerificationRepository verificationMessageRepository;
	
	@Autowired
	public VerificationMessageServiceImpl(
			VerificationRepository verificationMessageRepository
			) {
		this.verificationMessageRepository = verificationMessageRepository;
	}
	
	public VerificationMessageDto save(VerificationMessageDto verificationMessageDto) {
		return verificationMessageDto.fromEntity(verificationMessageRepository.save(verificationMessageDto.toEntity(verificationMessageDto)));
	}
	

	public VerificationMessageDto findById(Long id) {
		if (id == null) { 
	      return null;
	    }
	    return verificationMessageRepository.findById(id).map(VerificationMessageDto::fromEntity).orElseThrow(() -> 
			new NoDataFoundException("Aucune Verification Message Found With this Id = " + id)
	        );
	}
	
	public List<VerificationMessageDto> findByDeviceNumber(String deviceNumber) {
		if(deviceNumber == null)
			return null;
		return verificationMessageRepository.findByDeviceNumber(deviceNumber).stream().map(VerificationMessageDto::fromEntity).collect(Collectors.toList());
	}
	
	public List<VerificationMessageDto> findByBagNumber(String bagNumber) {
		if(bagNumber == null) 
			return null;
		return verificationMessageRepository.findByBagNumber(bagNumber).stream().map(VerificationMessageDto::fromEntity).collect(Collectors.toList());
	}

	public List<VerificationMessageDto> findAll() {
		return verificationMessageRepository.findAll().stream().map(VerificationMessageDto::fromEntity).collect(Collectors.toList());
	}
	
}
