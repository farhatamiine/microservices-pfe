package com.abdali.microhps.dropsadjustmentservice.model;

import java.math.BigDecimal; 

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="pre_cleared_transaction")
public class PreClearedTransaction extends AuditEntity {
	
	private String deviceNumber;
	private String bagNumber; 
//	private BigDecimal totalAmount;
	private Long merchantNumber;
	private BigDecimal creaditedAmount;
	private BigDecimal debitedAmount;
	private String AcountNumber;
	
}
