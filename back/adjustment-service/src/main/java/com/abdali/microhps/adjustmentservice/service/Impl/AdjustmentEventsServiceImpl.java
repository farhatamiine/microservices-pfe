package com.abdali.microhps.adjustmentservice.service.Impl;

import java.awt.event.AdjustmentEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.adjustmentservice.repository.AdjustmentEventRepository;
import com.abdali.microhps.adjustmentservice.service.AdjustmentEventsService;

@Service
public class AdjustmentEventsServiceImpl implements AdjustmentEventsService {
	
	AdjustmentEventRepository adjustmentEventRepository;
	
	@Autowired
	public AdjustmentEventsServiceImpl(AdjustmentEventRepository adjustmentEventRepository) {
		this.adjustmentEventRepository = adjustmentEventRepository;
	}
	
	public void save(AdjustmentEvent event) {
		adjustmentEventRepository.save(event);
	}
}
