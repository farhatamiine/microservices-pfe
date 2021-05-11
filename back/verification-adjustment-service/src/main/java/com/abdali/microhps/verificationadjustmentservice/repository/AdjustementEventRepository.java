package com.abdali.microhps.verificationadjustmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abdali.microhps.verificationadjustmentservice.model.AdjustementEvent;

public interface AdjustementEventRepository extends JpaRepository<AdjustementEvent, Integer> {

}
