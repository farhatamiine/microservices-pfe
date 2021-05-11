package com.abdali.microhps.modechecker.model;

import java.math.BigDecimal;

import lombok.Setter;

@Setter
public class TransactionModel {
	
	private String deviceNumber;
	private String bagNumber; 
	private BigDecimal totalAmount;
	private Long merchantNumber;
	
}
