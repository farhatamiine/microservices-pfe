package com.abdali.microhps.integrityservice.service;

import com.abdali.microhps.integrityservice.model.Transaction;

public interface TransactionService {
	
	Transaction transactionCreate(char indicator, char coinsIndicator, String[] messageArray);
}
