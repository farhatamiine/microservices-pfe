package com.abdali.microhps.devicemerchantservice.service;

import java.util.List;

import com.abdali.microhps.devicemerchantservice.dto.MerchantDto;

public interface MerchantService {
	
	MerchantDto save(MerchantDto merchantDto);
	
	List<MerchantDto> findAll();
	
	MerchantDto findById(Integer merhantId);
	
	MerchantDto findByMerchantNumber(Long merhantNumber);
	
	List<MerchantDto> findAllMerchantByIdDevice(Long idDevice);
	
	Boolean merchantCheckStatus(Long merchantNumber, List<String> status);
}
