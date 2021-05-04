package com.abdali.microhps.devicemerchantservice.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.abdali.microhps.devicemerchantservice.model.Merchant;
import com.abdali.microhps.devicemerchantservice.model.MerchantStatus;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MerchantDto {

	private Integer id;
	private Long merchantNumber;
	private String merchantName;
	private MerchantStatus status;
	
	private MerchantAccountDto merchantAccount;
	private List<DeviceDto> devices;
	
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
				.merchantAccount(MerchantAccountDto.fromEntity(merchant.getMerchantAccount()))
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
		merchant.setMerchantAccount(MerchantAccountDto.toEntity(merchantDto.getMerchantAccount()));
		return merchant;
		
	}
	
}
