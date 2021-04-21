package com.abdali.microhps.removalservice.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abdali.microhps.removalservice.model.RemovalMessage;

public interface RemovalRepository extends JpaRepository<RemovalMessage, Long> {
	
	List<RemovalMessage> findByMerchantNumber(Long Id);
	
	List<RemovalMessage> findByBagNumber(String bagNumber);
	
	List<RemovalMessage> findByDeviceNumber(String deviceNumber);
	
	List<RemovalMessage> findByMerchantNumberAndBagNumberAndTransactionIdAndTransmitionDate(Long Id, String bagNumber, Integer transactionId, Instant transmitionDate);
}
