package com.abdali.microhps.integrityservice.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.integrityservice.proxy.DropMessageProxy; 

//- Duplicated message: this message fails when an existing message is found with the following
//o Same merchant number.
//o Same bag number.
//o Same transaction id.
//o Same date and time.

@Service
public class DuplicatedMessage {

	private DropMessageProxy dropMessageProxy;
	
	@Autowired
	public DuplicatedMessage(
			DropMessageProxy dropMessageProxy
			) {
		// TODO Auto-generated constructor stub
		this.dropMessageProxy = dropMessageProxy;
	}
	
	public Boolean checkForDuplicatedMessage(
			Long merchantNumber, 
			String bagNumber, 
			String transmitionDate, 
			Integer transactionId) {
		
		return dropMessageProxy.getListDropMessages(merchantNumber, bagNumber, transactionId, transmitionDate);
	}

	
	
}
