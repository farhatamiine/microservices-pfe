package com.abdali.microhps.modechecker.model;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Setter;

@Setter
public class TransactionModel {

	private Integer transactionId;
	private String deviceNumber;
	private String bagNumber; 
	private BigDecimal totalAmount;
	private Long merchantNumber;
	private Instant transmitionDate;
	private String merchantSettlementMode;
}
