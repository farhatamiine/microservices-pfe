package com.abdali.microhps.verificationservice.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abdali.microhps.verificationservice.model.DropMessage;

public interface DropRepository extends JpaRepository<DropMessage, Long> {
	
	List<DropMessage> findByMerchantNumber(Long Id);
	
	List<DropMessage> findByBagNumber(String bagNumber);
	
	List<DropMessage> findByDeviceNumber(String deviceNumber);
	
	List<DropMessage> findByMerchantNumberAndBagNumberAndTransactionIdAndTransmitionDate(Long Id, String bagNumber, Integer transactionId, Instant transmitionDate);
}
