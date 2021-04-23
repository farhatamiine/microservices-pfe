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
    public Transaction transactionCreate(char coinsIndicator, String[] messageArray, String message) {
    		
    		char indicator = messageArray[0].charAt(0);
    	
    		for(int i =0; i < messageArray.length; i++) {
    			messageArray[i] = messageArray[i].trim().replace("_+$", "");
    		}
    		
    		Denomination denomination = new Denomination();	
    		
    		if(coinsIndicator == COINS_INDICATOR) {			
    			if(messageArray[(messageArray.length - 16)].contains("=")) {
    				denomination.setDenomination1(Integer.parseInt(messageArray[(messageArray.length - 16)].split("=")[1]));
    			}
    			if(messageArray[(messageArray.length - 15)].contains("=")) {
    				denomination.setDenomination2(Integer.parseInt(messageArray[(messageArray.length - 15)].split("=")[1]));
    			}
    			if(messageArray[(messageArray.length - 14)].contains("=")) {
    				denomination.setDenomination3(Integer.parseInt(messageArray[(messageArray.length - 14)].split("=")[1]));
    			}
    			if(messageArray[(messageArray.length - 13)].contains("=")) {
    				denomination.setDenomination4(Integer.parseInt(messageArray[(messageArray.length - 13)].split("=")[1]));
    			}
    			if(messageArray[(messageArray.length - 12)].contains("=")) {
    				denomination.setDenomination5(Integer.parseInt(messageArray[(messageArray.length - 12)].split("=")[1]));
    			}
    			if(messageArray[(messageArray.length - 11)].contains("=")) {
    				denomination.setDenomination6(Integer.parseInt(messageArray[(messageArray.length - 11)].split("=")[1]));
    			}
    		}
    		
    		if(coinsIndicator == NOTES_INDICATOR) {			
    			if(messageArray[(messageArray.length - 10)].contains("=")) {
    				denomination.setDenomination7(Integer.parseInt(messageArray[(messageArray.length - 10)].split("=")[1]));
    			}
    			if(messageArray[(messageArray.length - 9)].contains("=")) {
    				denomination.setDenomination8(Integer.parseInt(messageArray[(messageArray.length - 9)].split("=")[1]));
    			}
    			if(messageArray[(messageArray.length - 8)].contains("=")) {
    				denomination.setDenomination9(Integer.parseInt(messageArray[(messageArray.length - 8)].split("=")[1]));
    			}
    			if(messageArray[(messageArray.length - 7)].contains("=")) {
    				denomination.setDenomination10(Integer.parseInt(messageArray[(messageArray.length - 7)].split("=")[1]));
    			}
    			if(messageArray[(messageArray.length - 6)].contains("=")) {
    				denomination.setDenomination11(Integer.parseInt(messageArray[(messageArray.length - 6)].split("=")[1]));
    			}
    		}
    		
    		if(messageArray[(messageArray.length - 5)].contains("=")) {
    			denomination.setDenomination12(Integer.parseInt(messageArray[(messageArray.length - 5)].split("=")[1]));
    		}
    		if(messageArray[(messageArray.length - 4)].contains("=")) {
    			denomination.setDenomination13(Integer.parseInt(messageArray[(messageArray.length - 4)].split("=")[1]));
    		}
    		if(messageArray[(messageArray.length - 3)].contains("=")) {
    			denomination.setDenomination14(Integer.parseInt(messageArray[(messageArray.length - 3)].split("=")[1]));
    		}
    		if(messageArray[(messageArray.length - 2)].contains("=")) {
    			denomination.setDenomination15(Integer.parseInt(messageArray[28].split("=")[(messageArray.length - 2)]));
    		}
    		

			final DateTimeFormatter formatter = DateTimeFormatter
	                .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
	                .withZone(ZoneId.systemDefault());
			Instant transmitionDate = null;

			// add to audit table.
			Transaction transaction = new Transaction();
			transaction.setIndicator(indicator);
				
    		if(indicator == DROP_INDICATOR) {

    			RemovalDropTransaction removalDropTransaction = new RemovalDropTransaction();
    			removalDropTransaction.setDepositReference(messageArray[7]);
    			removalDropTransaction.setSequenceNumber(Integer.parseInt(messageArray[5]));
    			
    			DropTransaction dropTransaction = new DropTransaction();
    			dropTransaction.setMerchantNumber(Long.parseLong(messageArray[1]));
    			
    			try {
    				transmitionDate = Instant.from(formatter.parse(messageArray[8]));
    			} catch (Exception e) {
    			//	throw new IntegrityException(MESSAGE_INVALID_CODE, "error date time");
    			} 
    			
				transaction.setDeviceNumber(messageArray[2].trim().replace("_+$", ""));
				transaction.setBagNumber(messageArray[3].trim().replace("_+$", ""));
				transaction.setContainerType( messageArray[4].charAt(0));
				transaction.setTransactionId(Integer.parseInt(messageArray[6].trim().replace("_+$", "")));
				transaction.setTransmitionDate(transmitionDate);
				transaction.setCanisterNumber(Integer.parseInt(messageArray[9]));
				transaction.setCurrency(messageArray[10]);
				transaction.setDropTransaction(dropTransaction);
				transaction.setRemovalDropTransaction(removalDropTransaction);
    				
    		}
    		
    		if(indicator == REMOVAL_INDICATOR) {
    			
    			
    			RemovalDropTransaction removalDropTransaction = new RemovalDropTransaction();
    			removalDropTransaction.setDepositReference(messageArray[6]);
    			removalDropTransaction.setSequenceNumber(Integer.parseInt(messageArray[4]));

    			try {
    				transmitionDate = Instant.from(formatter.parse(messageArray[7]));
    			} catch (Exception e) {
    				//	throw new IntegrityException(MESSAGE_INVALID_CODE, "error date time");
    			} 
    			
				transaction.setDeviceNumber(messageArray[1].trim().replace("_+$", ""));
				transaction.setBagNumber(messageArray[2].trim().replace("_+$", ""));
				transaction.setContainerType( messageArray[3].charAt(0));
				transaction.setTransactionId(Integer.parseInt(messageArray[5].trim().replace("_+$", "")));
				transaction.setTransmitionDate(transmitionDate);
				transaction.setCanisterNumber(Integer.parseInt(messageArray[8]));
				transaction.setCurrency(messageArray[9]);
				transaction.setRemovalDropTransaction(removalDropTransaction);
    				
    		}
    		
    		if(indicator == VERIFICATION_INDICATOR) {

    			
    			VerificationTransaction verificationTransaction = new VerificationTransaction();
    			verificationTransaction.setCashCenterCode(messageArray[9]);
    			verificationTransaction.setCashCenterType(messageArray[8]);
    			try {
    				transmitionDate = Instant.from(formatter.parse(messageArray[5]));
    			} catch (Exception e) {
    				//	throw new IntegrityException(MESSAGE_INVALID_CODE, "error date time");
    			} 
    			
				transaction.setDeviceNumber(messageArray[1].trim().replace("_+$", ""));
				transaction.setBagNumber(messageArray[2].trim().replace("_+$", ""));
				transaction.setContainerType( messageArray[3].charAt(0));
				transaction.setTransactionId(Integer.parseInt(messageArray[4].trim().replace("_+$", "")));
				transaction.setTransmitionDate(transmitionDate);
				transaction.setCanisterNumber(Integer.parseInt(messageArray[6]));
				transaction.setCurrency(messageArray[7]);
				transaction.setVerificationTransaction(verificationTransaction);
    				
    		}

    		transaction.setTotalAmount(new BigDecimal(messageArray[(messageArray.length - 19)]));
    		transaction.setTotalNotes(Integer.parseInt(messageArray[(messageArray.length - 18)]));
    		transaction.setTotalCoins(Integer.parseInt(messageArray[(messageArray.length - 17)]));
    		transaction.setDenomination(denomination);
    		transaction.setMessage(message);
    		
        return transactionRepository.save(transaction);
    }
    
}	

