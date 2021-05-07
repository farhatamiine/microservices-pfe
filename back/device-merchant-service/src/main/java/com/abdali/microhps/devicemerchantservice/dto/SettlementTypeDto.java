package com.abdali.microhps.devicemerchantservice.dto;

import java.util.List;

import com.abdali.microhps.devicemerchantservice.model.SettlementType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettlementTypeDto {

	private Integer id;
	private String transactionType;

	@JsonIgnore
	private List<MerchantDto> merchants;
	
	public static SettlementTypeDto fromEntity(SettlementType settlementType) {
		if(settlementType == null) {
			return null;
		}
		
		return SettlementTypeDto.builder()
				.id(settlementType.getId())
				.transactionType(settlementType.getTransactionType())
				.build();
	}
	
	public static SettlementType toEntity(SettlementTypeDto settlementTypeDto) {
		if(settlementTypeDto == null) {
			return null;
		}
		
		SettlementType merchantTransactionType = new SettlementType();
		merchantTransactionType.setId(settlementTypeDto.getId());
		merchantTransactionType.setTransactionType(settlementTypeDto.getTransactionType());
		return merchantTransactionType;
	}
}
