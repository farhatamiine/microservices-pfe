package com.abdali.microhps.dropsadjustmentservice.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor; 

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CoreTransactionModel extends AuditEntity {
	
	private String deviceNumber;
	private String bagNumber; 
	private BigDecimal totalAmount;
	private Long merchantNumber;
	
}
