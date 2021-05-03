package com.abdali.microhps.devicemerchantservice.service.impl;

import org.springframework.stereotype.Service;


import com.abdali.microhps.devicemerchantservice.dto.PowerCardNotificationDto;
import com.abdali.microhps.devicemerchantservice.repository.PowerCardNotificationRepository;
import com.abdali.microhps.devicemerchantservice.service.PowerCArdNotificationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PowerCArdNotificationServiceImpl implements PowerCArdNotificationService {
	
	private PowerCardNotificationRepository powerCardNotificationRepository;
	
	public PowerCArdNotificationServiceImpl() {
		this.powerCardNotificationRepository = powerCardNotificationRepository;
	}
	
	public void save(PowerCardNotificationDto powerCardNotificationDto) {

		PowerCardNotificationDto.fromEntity(powerCardNotificationRepository.save(PowerCardNotificationDto.toEntity(powerCardNotificationDto)));
	}
	
}
