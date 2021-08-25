package com.abdali.microhps.removaladjustmentservice.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PreClearedTransaction {
	
	private Integer transactionId; 
	private String deviceNumber;
	private String bagNumber;  
	private Long merchantNumber;
	private BigDecimal creaditedAmount;
	private BigDecimal debitedAmount;
	// credited/debited.
	private String typeCD;
	private String accountNumber;
}