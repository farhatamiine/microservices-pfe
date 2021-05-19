package com.abdali.microhps.preclearedadjustmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abdali.microhps.preclearedadjustmentservice.model.PreClearedTransaction;

public interface PreClearedTransactionRepository extends JpaRepository<PreClearedTransaction, Integer> {

}
