package com.abdali.microhps.removalservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.removalservice.dto.RemovalCoreMessageDto;
import com.abdali.microhps.removalservice.exceptions.types.NoDataFoundException;
import com.abdali.microhps.removalservice.model.RemovalCoreMessage;
import com.abdali.microhps.removalservice.repository.RemovalRepository;
import com.abdali.microhps.removalservice.service.RemovalMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class RemovalMessageServiceImpl implements RemovalMessageService {

	RemovalRepository removalMessageRepository;
    ObjectMapper objectMapper;
	
	@Autowired
	public RemovalMessageServiceImpl(
			RemovalRepository removalMessageRepository,
		    ObjectMapper objectMapper
			) {
		this.removalMessageRepository = removalMessageRepository;
	    this.objectMapper = objectMapper;
	}
	
	public void save(ConsumerRecord<Long,String> consumerRecord) throws JsonMappingException, JsonProcessingException {
		RemovalCoreMessageDto removalMessageDto = objectMapper.readValue(consumerRecord.value(), RemovalCoreMessageDto.class);
		log.info("dropMessage : {} ", removalMessageDto);
		RemovalCoreMessageDto removalMessage = RemovalCoreMessageDto.fromEntity(removalMessageRepository.save(RemovalCoreMessageDto.toEntity(removalMessageDto)));
		log.info("Successfully Persisted the Removal Message {} ", removalMessage);   
	}

	public RemovalCoreMessageDto findById(Long id) {
		if (id == null) { 
	      return null;
	    }
	    return removalMessageRepository.findById(id).map(RemovalCoreMessageDto::fromEntity).orElseThrow(() -> 
			new NoDataFoundException("Aucune Removal Message Found With this Id = " + id)
	        );
	}
	
	public List<RemovalCoreMessageDto> findByDeviceNumber(String deviceNumber) {
		if(deviceNumber == null)
			return null;
		return removalMessageRepository.findByDeviceNumber(deviceNumber).stream().map(RemovalCoreMessageDto::fromEntity).collect(Collectors.toList());
	}
	
	public List<RemovalCoreMessageDto> findByBagNumber(String bagNumber) {
		if(bagNumber == null) 
			return null;
		return removalMessageRepository.findByBagNumber(bagNumber).stream().map(RemovalCoreMessageDto::fromEntity).collect(Collectors.toList());
	}

	public List<RemovalCoreMessageDto> findAll() {
		return removalMessageRepository.findAll().stream().map(RemovalCoreMessageDto::fromEntity).collect(Collectors.toList());
	}
	

	public Boolean findByTransactionId(Integer transactionId) {
		if(removalMessageRepository.transactionId(transactionId).equals(0)) {
			return false;
		}
		return true;
	}
	
	public RemovalCoreMessage findRemovalTransaction(String deviceNumber, String bagNumber, Integer transactionId) {
		return removalMessageRepository.findFirstByDeviceNumberAndBagNumberAndTransactionIdNotOrderByIdDesc(deviceNumber, bagNumber, transactionId);
	}
	
}
