package com.abdali.microhps.dropservice.service.impl;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abdali.microhps.dropservice.dto.DropMessageDto;
import com.abdali.microhps.dropservice.exceptions.types.NoDataFoundException;
import com.abdali.microhps.dropservice.model.DropMessage;
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

	public Map<String, Object> findAll(int page, int size) {
		
		List<DropMessageDto> devices = new ArrayList<DropMessageDto>();
	      
	      Pageable pagingSort = PageRequest.of(page, size);

	      Page<DropMessage> pageDevices = null;
	      
	      pageDevices = dropMessageRepository.findAll(pagingSort);

	      List<DropMessageDto> devicesDto = pageDevices.getContent().stream()
	    		  .map(DropMessageDto::fromEntity)
	    		  .collect(Collectors.toList());

	      Map<String, Object> response = new HashMap<>();
	      
	      response.put("transactions", devicesDto);
	      response.put("currentPage", pageDevices.getNumber());
	      response.put("totalItems", pageDevices.getTotalElements());
	      response.put("totalPages", pageDevices.getTotalPages());
	      
	      return response;
	}
	
	public List<DropMessageDto> findByMerchantNumberAndBagNumberAndTransactionIdAndTransmitionDate(Long merchantNumber, String bagNumber, Integer transactionId, Instant transmitionDate) {
		if(merchantNumber == null || bagNumber == null || transactionId == null || transmitionDate == null)
			return null;
		
		return dropMessageRepository.findByMerchantNumberAndBagNumberAndTransactionIdAndTransmitionDate(merchantNumber, bagNumber, transactionId, transmitionDate).stream()
				.map(DropMessageDto::fromEntity).collect(Collectors.toList());
	}
}
