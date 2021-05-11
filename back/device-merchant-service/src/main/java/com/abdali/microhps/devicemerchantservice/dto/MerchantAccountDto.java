package com.abdali.microhps.devicemerchantservice.dto;
 
import com.abdali.microhps.devicemerchantservice.model.MerchantAccount;
import com.abdali.microhps.devicemerchantservice.model.enumeration.AccountTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantAccountDto {
	
	private int id;
	private String accountNumber;
	private AccountTypeEnum accountType;
	private AccountLimitsDto accountLimits;
	@JsonIgnore
	private MerchantDto merchants;
	
	public static MerchantAccountDto fromEntity(MerchantAccount merchantAccount) {
		if(merchantAccount == null)
			return null;
		return MerchantAccountDto.builder()
				.id(merchantAccount.getId())
				.accountNumber(merchantAccount.getAccountNumber())
				.accountType(merchantAccount.getAccountType()) 
				.accountLimits(AccountLimitsDto.fromEntity(merchantAccount.getAccountLimits()))
				.build();
	}
	
	public static MerchantAccount toEntity(MerchantAccountDto merchantAccountDto) {
		if(merchantAccountDto == null) {
			return null;
		}
		MerchantAccount merchantAccount = new MerchantAccount(); 
		merchantAccount.setId(merchantAccountDto.getId());
		merchantAccount.setAccountNumber(merchantAccountDto.getAccountNumber());
		merchantAccount.setAccountType(merchantAccountDto.getAccountType());	
		merchantAccount.setAccountLimits(AccountLimitsDto.toEntity(merchantAccountDto.getAccountLimits()));
		return merchantAccount;
	}
}