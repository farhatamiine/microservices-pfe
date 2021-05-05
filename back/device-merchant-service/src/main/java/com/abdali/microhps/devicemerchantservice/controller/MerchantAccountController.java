package com.abdali.microhps.devicemerchantservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdali.microhps.devicemerchantservice.dto.MerchantAccountDto;
import com.abdali.microhps.devicemerchantservice.service.MerchantAccountService;

@CrossOrigin("*")
@RestController
@RequestMapping("/merchant-account")
public class MerchantAccountController {
	
	private MerchantAccountService merchantAccountService;
	
	@Autowired
	public MerchantAccountController(MerchantAccountService merchantAccountService) {
		this.merchantAccountService = merchantAccountService;
	}

	@PostMapping(value = "/merchants", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public MerchantAccountDto addMerchant(@RequestBody MerchantAccountDto merchantAccountDto ) {
		return merchantAccountService.save(merchantAccountDto);
	}
	
}
