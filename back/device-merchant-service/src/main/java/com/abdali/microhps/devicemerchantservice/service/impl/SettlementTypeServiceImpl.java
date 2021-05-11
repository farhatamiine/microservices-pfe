package com.abdali.microhps.devicemerchantservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.devicemerchantservice.dto.SettlementTypeDto;
import com.abdali.microhps.devicemerchantservice.repository.SettlementTypeRepository;
import com.abdali.microhps.devicemerchantservice.service.SettlementTypeService;

@Service
public class SettlementTypeServiceImpl implements SettlementTypeService {
	
	SettlementTypeRepository settlementTypeRepository;
	
	@Autowired
	public SettlementTypeServiceImpl(SettlementTypeRepository settlementTypeRepository) {
		this.settlementTypeRepository = settlementTypeRepository;
	}
	
	public SettlementTypeDto save(SettlementTypeDto settlementTypeDto) {
		return SettlementTypeDto.fromEntity(settlementTypeRepository.save(SettlementTypeDto.toEntity(settlementTypeDto)));
	}
}
