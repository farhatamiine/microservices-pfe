package com.abdali.microhps.verificationservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.verificationservice.dto.VerificationCoreTransactionDto;
import com.abdali.microhps.verificationservice.exceptions.types.NoDataFoundException;
import com.abdali.microhps.verificationservice.repository.VerificationRepository;
import com.abdali.microhps.verificationservice.service.VerificationMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class VerificationMessageServiceImpl implements VerificationMessageService {

	VerificationRepository verificationMessageRepository;
	ObjectMapper objectMapper;
	
	@Autowired
	public VerificationMessageServiceImpl(
			VerificationRepository verificationMessageRepository,
			ObjectMapper objectMapper
			) {
		this.verificationMessageRepository = verificationMessageRepository;
		this.objectMapper = objectMapper;
	}
	
	public void save(ConsumerRecord<Long,String> consumerRecord) throws JsonMappingException, JsonProcessingException {
		VerificationCoreTransactionDto verificationTransactionDto = objectMapper.readValue(consumerRecord.value(), VerificationCoreTransactionDto.class);
		log.info("dropMessage : {} ", verificationTransactionDto);
		VerificationCoreTransactionDto verificationTransaction = VerificationCoreTransactionDto.fromEntity(verificationMessageRepository.save(VerificationCoreTransactionDto.toEntity(verificationTransactionDto)));
		log.info("Successfully Persisted the Removal Message {} ", verificationTransaction);   
	}
	

	public VerificationCoreTransactionDto findById(Long id) {
		if (id == null) { 
	      return null;
	    }
	    return verificationMessageRepository.findById(id).map(VerificationCoreTransactionDto::fromEntity).orElseThrow(() -> 
			new NoDataFoundException("Aucune Verification Message Found With this Id = " + id)
	        );
	}
	
	public List<VerificationCoreTransactionDto> findByDeviceNumber(String deviceNumber) {
		if(deviceNumber == null)
			return null;
		return verificationMessageRepository.findByDeviceNumber(deviceNumber).stream().map(VerificationCoreTransactionDto::fromEntity).collect(Collectors.toList());
	}
	
	public List<VerificationCoreTransactionDto> findByBagNumber(String bagNumber) {
		if(bagNumber == null) 
			return null;
		return verificationMessageRepository.findByBagNumber(bagNumber).stream().map(VerificationCoreTransactionDto::fromEntity).collect(Collectors.toList());
	}

	public List<VerificationCoreTransactionDto> findAll() {
		return verificationMessageRepository.findAll().stream().map(VerificationCoreTransactionDto::fromEntity).collect(Collectors.toList());
	}
	
}
