package com.abdali.microhps.dropservice.repository;

import java.time.Instant; 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abdali.microhps.dropservice.model.DropCoreTransaction;
import java.sql.Timestamp;

public interface DropRepository extends JpaRepository<DropCoreTransaction, Long> {
	
	List<DropCoreTransaction> findByDropTransactionMerchantNumber(Long Id);
	
	List<DropCoreTransaction> findByBagNumber(String bagNumber);
	
	List<DropCoreTransaction> findByDeviceNumber(String deviceNumber);
	
	List<DropCoreTransaction> findByDropTransactionMerchantNumberAndBagNumberAndTransactionIdAndTransmitionDate(Long Id, String bagNumber, Integer transactionId, Instant transmitionDate);
	
	@Query("select count(m.id) FROM DropCoreTransaction m where m.transactionId = :TransactionId")
	Integer transactionId(@Param("TransactionId") Integer TransactionId);
	
	List<DropCoreTransaction> findByDeviceNumberAndBagNumberAndTransmitionDateBetween(String deviceNumber, String bagNumber, Timestamp startDate, Timestamp endDate);
	
	DropCoreTransaction findTopByDeviceNumberAndBagNumberOrderByIdDesc(String deviceNumber,String bagNumber);
	
	List<DropCoreTransaction> findByDeviceNumberAndBagNumberAndTransmitionDateBetweenAndRemovalDropTransactionSequenceNumberIs(String deviceNumber, String bagNumber, Instant startDateInstant, Instant endDateInstant, int sequenceNumber);
}
