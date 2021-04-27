package com.abdali.microhps.dropservice.service.impl;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.dropservice.dto.DropMessageDto;
import com.abdali.microhps.dropservice.exceptions.types.NoDataFoundException;
import com.abdali.microhps.dropservice.repository.DropRepository;
import com.abdali.microhps.dropservice.service.DropMessageService;


@Service
public class DropMessageServiceImpl implements DropMessageService {

	DropRepository dropMessageRepository;
	
	@Autowired
	public DropMessageServiceImpl(
			DropRepository dropMessageRepository
			) {
		this.dropMessageRepository = dropMessageRepository;
	}
	
	public DropMessageDto save(DropMessageDto dropMessageDto) {
		return dropMessageDto.fromEntity(dropMessageRepository.save(dropMessageDto.toEntity(dropMessageDto)));
	}
	

	public DropMessageDto findById(Long id) {
		if (id == null) { 
	      return null;
	    }
	    return dropMessageRepository.findById(id).map(DropMessageDto::fromEntity).orElseThrow(() -> 
			new NoDataFoundException("Aucune Drop Message Found With this Id = " + id)
	        );
	}

	public List<DropMessageDto> findByMerchantNumber(Long merchantNumber) {
		if (merchantNumber == null) {
		      return null;
		    }
			return dropMessageRepository.findByMerchantNumber(merchantNumber).stream().map(DropMessageDto::fromEntity).collect(Collectors.toList());
	}
	
	public List<DropMessageDto> findByDeviceNumber(String deviceNumber) {
		if(deviceNumber == null)
			return null;
		return dropMessageRepository.findByDeviceNumber(deviceNumber).stream().map(DropMessageDto::fromEntity).collect(Collectors.toList());
	}
	
	public List<DropMessageDto> findByBagNumber(String bagNumber) {
		if(bagNumber == null) 
			return null;
		return dropMessageRepository.findByBagNumber(bagNumber).stream().map(DropMessageDto::fromEntity).collect(Collectors.toList());
	}

	public List<DropMessageDto> findAll() {
		return dropMessageRepository.findAll().stream().map(DropMessageDto::fromEntity).collect(Collectors.toList());
	}
	
	public List<DropMessageDto> findByMerchantNumberAndBagNumberAndTransactionIdAndTransmitionDate(Long merchantNumber, String bagNumber, Integer transactionId, Instant transmitionDate) {
		if(merchantNumber == null || bagNumber == null || transactionId == null || transmitionDate == null)
			return null;
		
		return dropMessageRepository.findByMerchantNumberAndBagNumberAndTransactionIdAndTransmitionDate(merchantNumber, bagNumber, transactionId, transmitionDate).stream()
				.map(DropMessageDto::fromEntity).collect(Collectors.toList());
	}
}
