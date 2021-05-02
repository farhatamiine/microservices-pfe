package com.abdali.microhps.dropservice.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abdali.microhps.dropservice.model.DropCoreTransaction;

public interface DropRepository extends JpaRepository<DropCoreTransaction, Long> {
	
	List<DropCoreTransaction> findByDropTransactionMerchantNumber(Long Id);
	
	List<DropCoreTransaction> findByBagNumber(String bagNumber);
	
	List<DropCoreTransaction> findByDeviceNumber(String deviceNumber);
	
	List<DropCoreTransaction> findByDropTransactionMerchantNumberAndBagNumberAndTransactionIdAndTransmitionDate(Long Id, String bagNumber, Integer transactionId, Instant transmitionDate);
}
