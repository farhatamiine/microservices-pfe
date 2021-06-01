package com.abdali.microhps.devicemerchantservice.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.abdali.microhps.devicemerchantservice.model.FeesCalculation;
import com.abdali.microhps.devicemerchantservice.model.Merchant;
import com.abdali.microhps.devicemerchantservice.model.enumeration.MerchantStatus;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MerchantDto {

	private Integer id;
	private Long merchantNumber;
	private String merchantName;
	private MerchantStatus status;
	
	private List<MerchantAccountDto> merchantAccounts;
	private List<FeesCalculationDto> merchantFees;
	private List<DeviceDto> devices;
	private SettlementTypeDto settlementType;
	private AccountLimitsDto merchantLimits;
	
	public static MerchantDto fromEntity(Merchant merchant) {
		if(merchant == null) {
			return null;
		}
		
		return MerchantDto.builder()
				.id(merchant.getId())
				.merchantNumber(merchant.getMerchantNumber())
				.merchantName(merchant.getMerchantName())
				.status(merchant.getStatus())
				.devices(
			            merchant.getDevices() != null ?
			            	merchant.getDevices().stream()
			                    .map(DeviceDto::fromEntity)
			                    .collect(Collectors.toList()) : null
			        )
				.settlementType(SettlementTypeDto.fromEntity(merchant.getSettlementType()))
				.merchantAccounts(
						merchant.getMerchantAccounts() != null ?
								merchant.getMerchantAccounts().stream()
								.map(MerchantAccountDto::fromEntity)
								.collect(Collectors.toList()) : null
						)
				.merchantFees(
						merchant.getMerchantFees() != null ? 
								merchant.getMerchantFees().stream()
								.map(FeesCalculationDto::fromEntity)
								.collect(Collectors.toList()) : null
						)
				
				.merchantLimits(AccountLimitsDto.fromEntity(merchant.getMerchantLimits()))
				.build();
	}
	
	public static Merchant toEntity(MerchantDto merchantDto) {
		
		if (merchantDto == null) {
			return null;
		}
		Merchant merchant = new Merchant();
		merchant.setId(merchantDto.getId());
		merchant.setMerchantNumber(merchantDto.getMerchantNumber());
		merchant.setMerchantName(merchantDto.getMerchantName());
		merchant.setStatus(merchantDto.getStatus());
		merchant.setDevices(
				merchantDto.getDevices() != null ?
		            	merchantDto.getDevices().stream()
		            	.map(DeviceDto::toEntity)
		            	.collect(Collectors.toList()) : null
				);
		merchant.setSettlementType(SettlementTypeDto.toEntity(merchantDto.getSettlementType()));
		merchant.setMerchantAccounts(
				merchantDto.getMerchantAccounts() != null ?
						merchantDto.getMerchantAccounts().stream()
						.map(MerchantAccountDto::toEntity)
						.collect(Collectors.toList()) : null
				);
		merchant.setMerchantFees(
				merchantDto.getMerchantFees() != null ?
				merchantDto.getMerchantFees().stream()
				.map(FeesCalculationDto::toEntity)
				.collect(Collectors.toList()) : null
				);
		merchant.setMerchantLimits(AccountLimitsDto.toEntity(merchantDto.getMerchantLimits()));
		return merchant;
		
	}
	
}
