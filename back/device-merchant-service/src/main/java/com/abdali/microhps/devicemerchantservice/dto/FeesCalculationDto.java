package com.abdali.microhps.devicemerchantservice.dto;

import com.abdali.microhps.devicemerchantservice.model.FeesCalculation;
import com.abdali.microhps.devicemerchantservice.model.Merchant;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeesCalculationDto {

	private int taux;
	private Double fixedAmount;
	private int minAmount;
	private int maxAmount;
	private int seuil;
	@JsonIgnore
	private Merchant merchantFeesCalc;
	
	public static FeesCalculationDto fromEntity(FeesCalculation feesCalculation) {
		if(feesCalculation == null) {
			return null;
		}
		
		return FeesCalculationDto.builder()
				.taux(feesCalculation.getTaux())
				.fixedAmount(feesCalculation.getFixedAmount())
				.minAmount(feesCalculation.getMinAmount())
				.maxAmount(feesCalculation.getMaxAmount())
				.seuil(feesCalculation.getSeuil())
				.build();
	}
	
	public static FeesCalculation toEntity(FeesCalculationDto feesCalculationDto) {
		if(feesCalculationDto == null) {
			return null;
		}
		FeesCalculation feesCalculation = new FeesCalculation();
		
		feesCalculation.setTaux(feesCalculationDto.getTaux());
		feesCalculation.setFixedAmount(feesCalculationDto.getFixedAmount());
		feesCalculation.setMinAmount(feesCalculationDto.getMinAmount());
		feesCalculation.setMaxAmount(feesCalculationDto.getMaxAmount());
		feesCalculation.setSeuil(feesCalculationDto.getSeuil());
		return feesCalculation;
	}
}
