package com.abdali.microhps.devicemerchantservice.dto;

import com.abdali.microhps.devicemerchantservice.model.AccountLimits;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountLimitsDto {
	
	private int id;
	private Double minExchange;
	private Double maxExchange;
	@JsonIgnore
	private MerchantAccountDto merchantAccount;
	
	public static AccountLimitsDto fromEntity(AccountLimits accountLimits) {
		if(accountLimits == null)
			return null;
		return AccountLimitsDto.builder()
				.id(accountLimits.getId())
				.minExchange(accountLimits.getMinExchange())
				.maxExchange(accountLimits.getMaxExchange())  
				.build();
	}
	
	public static AccountLimits toEntity(AccountLimitsDto accountLimitsDto) {
		if(accountLimitsDto == null) {
			return null;
		}
		AccountLimits accountLimits = new AccountLimits();
		accountLimits.setId(accountLimitsDto.getId());
		accountLimits.setMinExchange(accountLimitsDto.getMinExchange());
		accountLimits.setMaxExchange(accountLimitsDto.getMaxExchange());	
		return accountLimits;
	}
}
