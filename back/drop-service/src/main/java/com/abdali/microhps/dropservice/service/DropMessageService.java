package com.abdali.microhps.dropservice.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.abdali.microhps.dropservice.dto.DropMessageDto;


public interface DropMessageService {

	DropMessageDto save(DropMessageDto dropMessageDto);

	DropMessageDto findById(Long id);

	List<DropMessageDto> findByMerchantNumber(Long merchantNumber);
	
	List<DropMessageDto> findByDeviceNumber(String deviceNumber);
	
	List<DropMessageDto> findByBagNumber(String bagNumber);
	
	Map<String, Object> findAll(int page, int size);
	
	List<DropMessageDto> findByMerchantNumberAndBagNumberAndTransactionIdAndTransmitionDate(Long merchantNumber, String bagNumber, Integer transactionId, Instant transmitionDate);
}
