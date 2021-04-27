package com.abdali.microhps.othermessagesservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.abdali.microhps.othermessagesservice.dto.OtherTransactionDto;
import com.abdali.microhps.othermessagesservice.repository.OthertransactionRepository;
import com.abdali.microhps.othermessagesservice.service.OtherTransactionService;

public class OtherTransactionServiceImpl implements OtherTransactionService {

	private OthertransactionRepository otherTransactionRepository;

    @Autowired
	public OtherTransactionServiceImpl(OthertransactionRepository otherTransactionRepository) {
		this.otherTransactionRepository = otherTransactionRepository;
	}
    
    public OtherTransactionDto otherTransactionCreate(OtherTransactionDto otherTransactionDto) {
    	return OtherTransactionDto.fromEntity(otherTransactionRepository.save(OtherTransactionDto.toEntity(otherTransactionDto)));
    }
}
