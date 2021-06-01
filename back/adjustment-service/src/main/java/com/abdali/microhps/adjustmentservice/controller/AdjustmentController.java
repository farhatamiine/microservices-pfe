package com.abdali.microhps.adjustmentservice.controller;

import java.awt.event.AdjustmentEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdali.microhps.adjustmentservice.service.AdjustmentEventsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/adjustment-event")
@Slf4j
public class AdjustmentController {

	AdjustmentEventsService adjustmentEventService;
	
	@Autowired
	public AdjustmentController(AdjustmentEventsService adjustmentEventService) {
		this.adjustmentEventService = adjustmentEventService;
	}
	
	@PostMapping("/new-adjustment")
	public void save(@RequestBody AdjustmentEvent message) {
		log.info("hello " + message);
		
		
	}
	
	
}
