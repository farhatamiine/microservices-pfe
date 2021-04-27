package com.abdali.microhps.verificationservice.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abdali.microhps.verificationservice.model.VerificationMessage;

public interface VerificationRepository extends JpaRepository<VerificationMessage, Long> {
	
	List<VerificationMessage> findByBagNumber(String bagNumber);
	
	List<VerificationMessage> findByDeviceNumber(String deviceNumber);
	
}
