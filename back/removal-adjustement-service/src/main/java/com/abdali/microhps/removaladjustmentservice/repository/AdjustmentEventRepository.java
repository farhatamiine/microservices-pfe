package com.abdali.microhps.removaladjustmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abdali.microhps.removaladjustmentservice.model.AdjustmentEvent;

public interface AdjustmentEventRepository extends JpaRepository<AdjustmentEvent, Integer> {

}
