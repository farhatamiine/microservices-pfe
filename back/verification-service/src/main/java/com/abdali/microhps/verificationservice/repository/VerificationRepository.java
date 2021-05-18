package com.abdali.microhps.verificationservice.repository;
 
import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; 
import com.abdali.microhps.verificationservice.model.VerificationCoreTransaction;

public interface VerificationRepository extends JpaRepository<VerificationCoreTransaction, Long> {
	
	List<VerificationCoreTransaction> findByBagNumber(String bagNumber);
	
	List<VerificationCoreTransaction> findByDeviceNumber(String deviceNumber);
	
	@Query("select count(m.id) FROM VerificationCoreTransaction m where m.transactionId = :TransactionId")
	Integer transactionId(@Param("TransactionId") Integer TransactionId);


	VerificationCoreTransaction findFirstByDeviceNumberAndBagNumberAndTransactionIdNotOrderByIdDesc(String deviceNumber, String bagNumber, Integer transactionId);
}
