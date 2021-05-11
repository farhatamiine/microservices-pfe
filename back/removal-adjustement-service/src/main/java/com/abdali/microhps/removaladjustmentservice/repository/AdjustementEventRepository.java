package com.abdali.microhps.removaladjustmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abdali.microhps.removaladjustmentservice.model.AdjustementEvent;

public interface AdjustementEventRepository extends JpaRepository<AdjustementEvent, Integer> {

}
