package com.abdali.microhps.integrityservice.dto;

import javax.persistence.Column;

import com.abdali.microhps.integrityservice.model.Denomination;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DenominationDto {
 
	private Integer denomination1; 
	private Integer denomination2; 
	private Integer denomination3; 
	private Integer denomination4; 
	private Integer denomination5; 
	private Integer denomination6; 
	private Integer denomination7; 
	private Integer denomination8; 
	private Integer denomination9; 
	private Integer denomination10; 
	private Integer denomination11; 
	private Integer denomination12; 
	private Integer denomination13; 
	private Integer denomination14; 
	private Integer denomination15;
	
	public static DenominationDto fromEntity(Denomination denomination) {
		if(denomination == null) {
			return null;
		}
		
		return DenominationDto.builder()
				.denomination1(denomination.getDenomination1())
				.denomination2(denomination.getDenomination2())
				.denomination3(denomination.getDenomination3())
				.denomination4(denomination.getDenomination4())
				.denomination5(denomination.getDenomination5())
				.denomination6(denomination.getDenomination6())
				.denomination7(denomination.getDenomination7())
				.denomination8(denomination.getDenomination8())
				.denomination9(denomination.getDenomination9())
				.denomination10(denomination.getDenomination10())
				.denomination11(denomination.getDenomination11())
				.denomination12(denomination.getDenomination12())
				.denomination13(denomination.getDenomination13())
				.denomination14(denomination.getDenomination14())
				.denomination15(denomination.getDenomination15())
				.build();
	}
	
	public static Denomination toEntity(DenominationDto denominationDto) {
		if(denominationDto == null) {
			return null;
		}
		Denomination denomination = new Denomination();
		denomination.setDenomination1(denominationDto.getDenomination1());
		denomination.setDenomination1(denominationDto.getDenomination1());
		denomination.setDenomination2(denominationDto.getDenomination2());
		denomination.setDenomination3(denominationDto.getDenomination3());
		denomination.setDenomination4(denominationDto.getDenomination4());
		denomination.setDenomination5(denominationDto.getDenomination5());
		denomination.setDenomination6(denominationDto.getDenomination6());
		denomination.setDenomination7(denominationDto.getDenomination7());
		denomination.setDenomination8(denominationDto.getDenomination8());
		denomination.setDenomination9(denominationDto.getDenomination9());
		denomination.setDenomination10(denominationDto.getDenomination10());
		denomination.setDenomination11(denominationDto.getDenomination11());
		denomination.setDenomination12(denominationDto.getDenomination12());
		denomination.setDenomination13(denominationDto.getDenomination13());
		denomination.setDenomination14(denominationDto.getDenomination14());
		denomination.setDenomination15(denominationDto.getDenomination15());
		
		return denomination;
	}

}
