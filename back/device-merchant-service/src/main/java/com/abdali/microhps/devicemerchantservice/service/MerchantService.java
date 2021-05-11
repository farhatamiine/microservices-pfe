package com.abdali.microhps.devicemerchantservice.service;

import java.util.Map;

import com.abdali.microhps.devicemerchantservice.dto.MerchantDto;
import com.abdali.microhps.devicemerchantservice.model.enumeration.MerchantStatus;

public interface MerchantService {
	
	MerchantDto save(MerchantDto merchantDto);
	
	Map<String, Object> findAll(Long merchantNumber, int page, int size);
	
	MerchantDto findById(Integer merhantId);
	
	MerchantDto findByMerchantNumber(Long merhantNumber);
	
	Boolean merchantCheckStatus(Long merchantNumber, MerchantStatus status);
}
