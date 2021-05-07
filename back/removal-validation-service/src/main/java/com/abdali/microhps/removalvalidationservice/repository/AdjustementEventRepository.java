package com.abdali.microhps.removalvalidationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abdali.microhps.removalvalidationservice.model.AdjustementEvent;

public interface AdjustementEventRepository extends JpaRepository<AdjustementEvent, Integer> {

}
