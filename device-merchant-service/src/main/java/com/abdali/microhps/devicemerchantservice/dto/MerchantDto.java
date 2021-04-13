package com.abdali.microhps.devicemerchantservice.dto;

import com.abdali.microhps.devicemerchantservice.model.Merchant;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MerchantDto {

	private Integer id;
	private Long merchantNumber;
	private String merchantName;
	private String status;
	private DeviceDto device;
	
	public static MerchantDto fromEntity(Merchant merchant) {
		if(merchant == null) {
			return null;
		}
		
		return MerchantDto.builder()
				.id(merchant.getId())
				.merchantNumber(merchant.getMerchantNumber())
				.merchantName(merchant.getMerchantName())
				.status(merchant.getStatus())
				.device(DeviceDto.fromEntity(merchant.getDevice()))
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
		merchant.setDevice(DeviceDto.toEntity(merchantDto.getDevice()));
		return merchant;
		
	}
	
}
