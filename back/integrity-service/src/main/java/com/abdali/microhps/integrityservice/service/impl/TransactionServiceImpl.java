package com.abdali.microhps.integrityservice.service.impl;

import static com.abdali.microhps.integrityservice.utils.Constants.COINS_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.NOTES_INDICATOR;

import static com.abdali.microhps.integrityservice.utils.Constants.DROP_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.REMOVAL_INDICATOR;
import static com.abdali.microhps.integrityservice.utils.Constants.VERIFICATION_INDICATOR;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.integrityservice.model.Denomination;
import com.abdali.microhps.integrityservice.model.DropTransaction;
import com.abdali.microhps.integrityservice.model.RemovalDropTransaction;
import com.abdali.microhps.integrityservice.model.Transaction;
import com.abdali.microhps.integrityservice.model.VerificationTransaction;
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

