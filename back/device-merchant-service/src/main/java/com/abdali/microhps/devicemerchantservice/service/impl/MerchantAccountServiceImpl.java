package com.abdali.microhps.devicemerchantservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.devicemerchantservice.dto.MerchantAccountDto;
import com.abdali.microhps.devicemerchantservice.repository.MerchantAccountRepository;
import com.abdali.microhps.devicemerchantservice.service.MerchantAccountService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MerchantAccountServiceImpl implements MerchantAccountService {
	private MerchantAccountRepository merchantAccountRepository;
	
	@Autowired
	public MerchantAccountServiceImpl(
			MerchantAccountRepository merchantAccountRepository
			) {
		this.merchantAccountRepository = merchantAccountRepository;
	}
	
	@Override
	public MerchantAccountDto save(MerchantAccountDto merchantAccountDto) {
		return MerchantAccountDto.fromEntity(merchantAccountRepository.save(MerchantAccountDto.toEntity(merchantAccountDto)));
	}
}
