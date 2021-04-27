package com.abdali.microhps.integrityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abdali.microhps.integrityservice.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
