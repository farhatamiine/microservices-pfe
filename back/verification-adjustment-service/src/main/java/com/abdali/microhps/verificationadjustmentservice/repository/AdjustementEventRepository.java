package com.abdali.microhps.verificationadjustmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.abdali.microhps.verificationadjustmentservice.model.AdjustmentEvent;

public interface AdjustementEventRepository extends JpaRepository<AdjustmentEvent, Integer> {

}
