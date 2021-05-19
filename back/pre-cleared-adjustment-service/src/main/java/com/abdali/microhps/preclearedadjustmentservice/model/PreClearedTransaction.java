package com.abdali.microhps.preclearedadjustmentservice.model;

import java.math.BigDecimal; 

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="pre_cleared_transaction")
public class PreClearedTransaction extends AuditEntity {
	
	private String deviceNumber;
	private String bagNumber;  
	private Long merchantNumber;
	private BigDecimal creaditedAmount;
	private BigDecimal debitedAmount;
	private String accountNumber;
	
}
