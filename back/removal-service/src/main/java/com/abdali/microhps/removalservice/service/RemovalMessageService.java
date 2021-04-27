package com.abdali.microhps.removalservice.service;

import java.time.Instant;
import java.util.List;

import com.abdali.microhps.removalservice.dto.RemovalMessageDto;


public interface RemovalMessageService {

	RemovalMessageDto save(RemovalMessageDto removalMessageDto);

	RemovalMessageDto findById(Long id);
	
	List<RemovalMessageDto> findByDeviceNumber(String deviceNumber);
	
	List<RemovalMessageDto> findByBagNumber(String bagNumber);
	
	List<RemovalMessageDto> findAll();
}
