package com.abdali.microhps.removalservice.service.impl;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.removalservice.dto.RemovalMessageDto;
import com.abdali.microhps.removalservice.exceptions.types.NoDataFoundException;
import com.abdali.microhps.removalservice.repository.RemovalRepository;
import com.abdali.microhps.removalservice.service.RemovalMessageService;


@Service
public class RemovalMessageServiceImpl implements RemovalMessageService {

	RemovalRepository removalMessageRepository;
	
	@Autowired
	public RemovalMessageServiceImpl(
			RemovalRepository removalMessageRepository
			) {
		this.removalMessageRepository = removalMessageRepository;
	}
	
	public RemovalMessageDto save(RemovalMessageDto removalMessageDto) {
		return removalMessageDto.fromEntity(removalMessageRepository.save(removalMessageDto.toEntity(removalMessageDto)));
	}
	

	public RemovalMessageDto findById(Long id) {
		if (id == null) { 
	      return null;
	    }
	    return removalMessageRepository.findById(id).map(RemovalMessageDto::fromEntity).orElseThrow(() -> 
			new NoDataFoundException("Aucune Removal Message Found With this Id = " + id)
	        );
	}
	
	public List<RemovalMessageDto> findByDeviceNumber(String deviceNumber) {
		if(deviceNumber == null)
			return null;
		return removalMessageRepository.findByDeviceNumber(deviceNumber).stream().map(RemovalMessageDto::fromEntity).collect(Collectors.toList());
	}
	
	public List<RemovalMessageDto> findByBagNumber(String bagNumber) {
		if(bagNumber == null) 
			return null;
		return removalMessageRepository.findByBagNumber(bagNumber).stream().map(RemovalMessageDto::fromEntity).collect(Collectors.toList());
	}

	public List<RemovalMessageDto> findAll() {
		return removalMessageRepository.findAll().stream().map(RemovalMessageDto::fromEntity).collect(Collectors.toList());
	}
	
}
