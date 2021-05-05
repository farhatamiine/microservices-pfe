package com.abdali.microhps.devicemerchantservice.dto;

import com.abdali.microhps.devicemerchantservice.model.MerchantAccount;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantAccountDto {
	
	private int id;
	private Double min_exchange;
	private Double max_exchange;
	@JsonIgnore
	private MerchantDto merchant;
	
	public static MerchantAccountDto fromEntity(MerchantAccount merchantAccount) {
		if(merchantAccount == null)
			return null;
		return MerchantAccountDto.builder()
				.id(merchantAccount.getId())
				.min_exchange(merchantAccount.getMin_exchange())
				.max_exchange(merchantAccount.getMax_exchange()) 
//				.merchant(MerchantDto.fromEntity(merchantAccount.getMerchant()))
				.build();
	}
	
	public static MerchantAccount toEntity(MerchantAccountDto merchantAccountDto) {
		if(merchantAccountDto == null) {
			return null;
		}
		MerchantAccount merchantAccount = new MerchantAccount();
		merchantAccount.setId(merchantAccountDto.getId());
		merchantAccount.setMin_exchange(merchantAccountDto.getMin_exchange());
		merchantAccount.setMax_exchange(merchantAccountDto.getMax_exchange());	
//		merchantAccount.setMerchant(MerchantDto.toEntity(merchantAccountDto.getMerchant()));
		return merchantAccount;
	}
}
