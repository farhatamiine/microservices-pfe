package com.abdali.microhps.integrityservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.integrityservice.model.Transaction;
import com.abdali.microhps.integrityservice.repository.TransactionRepository;
import com.abdali.microhps.integrityservice.service.TransactionService;
 
@Service
public class TransactionServiceImpl implements TransactionService {
	
    private TransactionRepository transactionRepository;

    @Autowired
	public TransactionServiceImpl(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
    
    @Override
    public Transaction transactionCreate(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
    
}	

