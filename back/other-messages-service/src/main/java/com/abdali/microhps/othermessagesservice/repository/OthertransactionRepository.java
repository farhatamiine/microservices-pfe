package com.abdali.microhps.othermessagesservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.abdali.microhps.othermessagesservice.model.OtherTransaction;

public interface OthertransactionRepository extends JpaRepository<OtherTransaction, Integer> {
	
}