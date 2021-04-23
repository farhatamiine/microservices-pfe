package com.abdali.microhps.integrityservice.service;

import com.abdali.microhps.integrityservice.model.Transaction;

public interface TransactionService {
	
	Transaction transactionCreate(char coinsIndicator, String[] messageArray, String message);
}
