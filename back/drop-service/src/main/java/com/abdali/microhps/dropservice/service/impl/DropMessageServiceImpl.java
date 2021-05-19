package com.abdali.microhps.dropservice.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abdali.microhps.dropservice.dto.DropCoreTransactionDto;
import com.abdali.microhps.dropservice.exceptions.types.NoDataFoundException;
import com.abdali.microhps.dropservice.model.DropCoreTransaction;
import com.abdali.microhps.dropservice.repository.DropRepository;
import com.abdali.microhps.dropservice.service.DropMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DropMessageServiceImpl implements DropMessageService {

	DropRepository dropMessageRepository;
    ObjectMapper objectMapper;
	
	@Autowired
	public DropMessageServiceImpl(
			DropRepository dropMessageRepository,
			ObjectMapper objectMapper
			) {
		this.dropMessageRepository = dropMessageRepository;
		this.objectMapper = objectMapper;
	}
	
//	public DropMessageDto save(DropMessageDto dropMessageDto) {
//		return dropMessageDto.fromEntity(dropMessageRepository.save(dropMessageDto.toEntity(dropMessageDto)));
//	}
	
	public void save(ConsumerRecord<Long,String> consumerRecord) throws JsonMappingException, JsonProcessingException {
		
		DropCoreTransactionDto dropMessageDto = objectMapper.readValue(consumerRecord.value(), DropCoreTransactionDto.class);
		
		log.info("dropMessage : {} ", dropMessageDto);
		
		DropCoreTransactionDto dropMessage = DropCoreTransactionDto.fromEntity(dropMessageRepository.save(DropCoreTransactionDto.toEntity(dropMessageDto)));
		log.info("Successfully Persisted the Drop Transaction {} ", dropMessage);   
	}
//	
//	public void save2(DropCoreTransactionDto dropMessageDto) throws JsonMappingException, JsonProcessingException {
//		
//		DropTransactionCoreDto dropMessageDto = objectMapper.readValue(consumerRecord.value(), DropTransactionCoreDto.class);
//		
////		log.info("dropMessage____________________ : {} ", dropMessageDto);
//		DropCoreTransactionDto dropMessage = DropCoreTransactionDto.fromEntity(dropMessageRepository.save(DropCoreTransactionDto.toEntity(dropMessageDto)));
//		log.info("Successfully Persisted the libary Event {} ", dropMessage);   
//	}

	public DropCoreTransactionDto findById(Long id) {
		if (id == null) { 
	      return null;
	    }
	    return dropMessageRepository.findById(id).map(DropCoreTransactionDto::fromEntity).orElseThrow(() -> 
			new NoDataFoundException("Aucune Drop Message Found With this Id = " + id)
	        );
	}

	public List<DropCoreTransactionDto> findByMerchantNumber(Long merchantNumber) {
		if (merchantNumber == null) {
		      return null;
		    }
			return dropMessageRepository.findByDropTransactionMerchantNumber(merchantNumber).stream().map(DropCoreTransactionDto::fromEntity).collect(Collectors.toList());
	}
	
	public List<DropCoreTransactionDto> findByDeviceNumber(String deviceNumber) {
		if(deviceNumber == null)
			return null;
		return dropMessageRepository.findByDeviceNumber(deviceNumber).stream().map(DropCoreTransactionDto::fromEntity).collect(Collectors.toList());
	}
	
	public List<DropCoreTransactionDto> findByBagNumber(String bagNumber) {
		if(bagNumber == null) 
			return null;
		return dropMessageRepository.findByBagNumber(bagNumber).stream().map(DropCoreTransactionDto::fromEntity).collect(Collectors.toList());
	}

	public Map<String, Object> findAll(int page, int size) {
		
		List<DropCoreTransactionDto> devices = new ArrayList<DropCoreTransactionDto>();
	      
	      Pageable pagingSort = PageRequest.of(page, size);

	      Page<DropCoreTransaction> pageDevices = null;
	      
	      pageDevices = dropMessageRepository.findAll(pagingSort);

	      List<DropCoreTransactionDto> devicesDto = pageDevices.getContent().stream()
	    		  .map(DropCoreTransactionDto::fromEntity)
	    		  .collect(Collectors.toList());

	      Map<String, Object> response = new HashMap<>();
	      
	      response.put("transactions", devicesDto);
	      response.put("currentPage", pageDevices.getNumber());
	      response.put("totalItems", pageDevices.getTotalElements());
	      response.put("totalPages", pageDevices.getTotalPages());
	      
	      return response;
	}
	
	public List<DropCoreTransactionDto> findByMerchantNumberAndBagNumberAndTransactionIdAndTransmitionDate(Long merchantNumber, String bagNumber, Integer transactionId, Instant transmitionDate) {
		if(merchantNumber == null || bagNumber == null || transactionId == null || transmitionDate == null)
			return null;
		
		return dropMessageRepository.findByDropTransactionMerchantNumberAndBagNumberAndTransactionIdAndTransmitionDate(merchantNumber, bagNumber, transactionId, transmitionDate).stream()
				.map(DropCoreTransactionDto::fromEntity).collect(Collectors.toList());
	}
	
	public Boolean findByTransactionId(Integer transactionId) { 
		if(dropMessageRepository.transactionId(transactionId).equals(0)) {
			return false;
		}
		return true; 
	}
	
	public List<DropCoreTransactionDto> listDropsBetwwenDates(String deviceNumber, String bagNumber, Instant startDate, Instant endDate) {
		Timestamp endTime = Timestamp.from(endDate);
		Timestamp startTime = Timestamp.from(startDate);
	   return dropMessageRepository.findByDeviceNumberAndBagNumberAndTransmitionDateBetween(deviceNumber, bagNumber, startTime, endTime).stream()
				.map(DropCoreTransactionDto::fromEntity).collect(Collectors.toList());
	}
	
	public Long getMerchantNumber(String deviceNumber, String bagNumber) {
		DropCoreTransaction lastDrop = dropMessageRepository.findTopByDeviceNumberAndBagNumberOrderByIdDesc(deviceNumber, bagNumber);
		return lastDrop.getDropTransaction().getMerchantNumber();
	}
	
	public Instant getFirstDropMessageDate(String deviceNumber, String bagNumber, Instant startDateInstant, Instant endDateInstant) {
		int sequenceNumber = 1;
		List<DropCoreTransaction> listMessages = dropMessageRepository.findByDeviceNumberAndBagNumberAndTransmitionDateBetweenAndRemovalDropTransactionSequenceNumberIs(deviceNumber, bagNumber, startDateInstant, endDateInstant, sequenceNumber);
		DropCoreTransaction targetTransaction = null;
		try { 
			targetTransaction = listMessages.get(1);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return targetTransaction.getTransmitionDate();
	}
}
