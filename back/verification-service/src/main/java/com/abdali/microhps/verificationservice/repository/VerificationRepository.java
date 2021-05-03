package com.abdali.microhps.verificationservice.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abdali.microhps.verificationservice.model.VerificationCoreTransaction;

public interface VerificationRepository extends JpaRepository<VerificationCoreTransaction, Long> {
	
	List<VerificationCoreTransaction> findByBagNumber(String bagNumber);
	
	List<VerificationCoreTransaction> findByDeviceNumber(String deviceNumber);
	
}
