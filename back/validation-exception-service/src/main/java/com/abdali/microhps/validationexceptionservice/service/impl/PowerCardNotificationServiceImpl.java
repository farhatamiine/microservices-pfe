package com.abdali.microhps.validationexceptionservice.service.impl;

import org.springframework.stereotype.Service;

import com.abdali.microhps.validationexceptionservice.model.PowerCardNotification;
import com.abdali.microhps.validationexceptionservice.repository.PowerCardNotificationRepository;
import com.abdali.microhps.validationexceptionservice.service.PowerCardNotificationService;

@Service
public class PowerCardNotificationServiceImpl implements PowerCardNotificationService {
	
	private PowerCardNotificationRepository powerCardNotificationRepository;
	
	public PowerCardNotificationServiceImpl() {
		this.powerCardNotificationRepository = powerCardNotificationRepository;
	}
	
	@Override
	public void save(PowerCardNotification powerCardNotification) {
		powerCardNotificationRepository.save(powerCardNotification);
	}
}
