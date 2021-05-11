package com.abdali.microhps.devicemerchantservice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abdali.microhps.devicemerchantservice.dto.DeviceDto;
import com.abdali.microhps.devicemerchantservice.dto.SettlementTypeDto;
import com.abdali.microhps.devicemerchantservice.service.SettlementTypeService;

@RestController
@CrossOrigin("*")
public class SettlementTypeController {
	
	SettlementTypeService settlementTypeService;
	
	public SettlementTypeController(SettlementTypeService settlementTypeService) {
		this.settlementTypeService = settlementTypeService;
	}
	
	@PostMapping("/merchant-device/settlement")
    public SettlementTypeDto addSettlement(@RequestBody SettlementTypeDto settlementTypeDto) {
        return settlementTypeService.save(settlementTypeDto);
    }
}
