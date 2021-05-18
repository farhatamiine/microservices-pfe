package com.abdali.microhps.removalservice.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
 
import com.abdali.microhps.removalservice.model.RemovalCoreMessage;

public interface RemovalRepository extends JpaRepository<RemovalCoreMessage, Long> {
	
	List<RemovalCoreMessage> findByBagNumber(String bagNumber);
	
	List<RemovalCoreMessage> findByDeviceNumber(String deviceNumber);
	
	@Query("select count(m.id) FROM RemovalCoreMessage m where m.transactionId = :TransactionId")
	Integer transactionId(@Param("TransactionId") Integer TransactionId);

	RemovalCoreMessage findFirstByDeviceNumberAndBagNumberAndTransactionIdNotOrderByIdDesc(String deviceNumber, String bagNumber, Integer transactionId);
	
	RemovalCoreMessage findByDeviceNumberAndBagNumberAndTransmitionDateBetween(String deviceNumber, String bagNumber, Timestamp startDate, Timestamp endDate);
	
}
