package com.abdali.microhps.removalservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abdali.microhps.removalservice.model.RemovalCoreMessage;

public interface RemovalRepository extends JpaRepository<RemovalCoreMessage, Long> {
	
	List<RemovalCoreMessage> findByBagNumber(String bagNumber);
	
	List<RemovalCoreMessage> findByDeviceNumber(String deviceNumber);
	
}
