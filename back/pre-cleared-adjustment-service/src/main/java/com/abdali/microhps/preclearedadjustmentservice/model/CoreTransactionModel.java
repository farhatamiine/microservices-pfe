package com.abdali.microhps.preclearedadjustmentservice.model;

import java.math.BigDecimal; 

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor; 

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CoreTransactionModel {
	
	private String deviceNumber;
	private String bagNumber; 
	private BigDecimal totalAmount;
	private Long merchantNumber;
	// credited/debited.
	private String typeCD;
}
