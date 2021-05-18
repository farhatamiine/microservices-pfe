package com.abdali.microhps.dropsadjustmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository; 
import com.abdali.microhps.dropsadjustmentservice.model.PreClearedTransaction;

public interface PreClearedTransactionRepository extends JpaRepository<PreClearedTransaction, Integer> {

}
