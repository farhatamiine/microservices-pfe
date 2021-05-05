package com.abdali.microhps.validationexceptionservice.validation;
import static com.abdali.microhps.validationexceptionservice.utils.Constants.DROP_INDICATOR;
import static com.abdali.microhps.validationexceptionservice.utils.Constants.REMOVAL_INDICATOR;
import static com.abdali.microhps.validationexceptionservice.utils.Constants.VERIFICATION_INDICATOR;
import org.springframework.stereotype.Service;

import com.abdali.microhps.validationexceptionservice.model.PowerCardNotification;
import com.abdali.microhps.validationexceptionservice.proxy.DropTransactionProxy;
import com.abdali.microhps.validationexceptionservice.proxy.RemovalTransactionProxy;
import com.abdali.microhps.validationexceptionservice.proxy.VerificationTransactionProxy;
import com.abdali.microhps.validationexceptionservice.repository.PowerCardNotificationRepository;

@Service
public class TransactionIdDuplicated {
	
	DropTransactionProxy dropTransactionProxy;
	RemovalTransactionProxy removalTransactionProxy;
	VerificationTransactionProxy verificationTransactionProxy;
	PowerCardNotificationRepository powerCardNotificationRepository;
	
	public TransactionIdDuplicated(
			DropTransactionProxy dropTransactionProxy, 
			RemovalTransactionProxy removalTransactionProxy,
			VerificationTransactionProxy verificationTransactionProxy,
			PowerCardNotificationRepository powerCardNotificationRepository
			) {
		this.dropTransactionProxy = dropTransactionProxy;
		this.removalTransactionProxy = removalTransactionProxy;
		this.verificationTransactionProxy = verificationTransactionProxy;
		this.powerCardNotificationRepository = powerCardNotificationRepository;
	}

	public Boolean isTransactionIdDuplicated(char indicator, String transactionId) {
		int transactionID = Integer.parseInt(transactionId);
		if(indicator == DROP_INDICATOR) { 
			/**
				drop service will return false if its not duplicated and true if its duplicated.
				we need to stop process and generate exception if the transaction is duplicated.
				isduplicated ---> false ---> return true
			*/
			if(dropTransactionProxy.isTransactionIdDuplicated(transactionID)) {
				this.makeNotification("Drop", transactionID);
				return false;
			}
			return true; // we will return true to say that everyting is ok
		} else if(indicator == REMOVAL_INDICATOR) {
			if(removalTransactionProxy.isTransactionIdDuplicated(transactionID)) {
				this.makeNotification("Removal", transactionID);
				// TODO : ADD Notification in PowerCard.
				return false;
			}
			return true; 
		} else if(indicator == VERIFICATION_INDICATOR) {
			if(verificationTransactionProxy.isTransactionIdDuplicated(transactionID)) {
				this.makeNotification("Verification", transactionID);
				// TODO : ADD Notification in PowerCard.
				return false;
			}
			return true;
		} 
		return false;
	}
	
	public void makeNotification(String transactionType, int transactionID) {
		// TODO : ADD Notification in PowerCard.
		PowerCardNotification powerCardNotification = new PowerCardNotification();
		powerCardNotification.setNotificationTitle("Transaction Duplicated");
		powerCardNotification.setNotificationDescription("The transaction with Id : " + transactionID + " is duplicated in " + transactionType + " Transactions");
		powerCardNotification.setTransactionId(transactionID);
		powerCardNotificationRepository.save(powerCardNotification);
	}
	
}