package com.abdali.microhps.verificationadjustmentservice.model;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.abdali.microhps.verificationadjustmentservice.model.enumeration.TransferSign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="REMOVAL_ADJUSTEMENT_EVENTS")
public class AdjustmentEvent extends AuditEntity {
	
	private String eventIndicator;
	private char indicator = 'A';
	private Long referenceNumber;
	private TransferSign transferSign; 
	private BigDecimal transferAmount;
	@Column(columnDefinition = "varchar(3)")
	private String transferCurrency;
	@Column(columnDefinition = "smallint")
	private int currencyExponent;
	private Long merchantNumber;
	private String accountNumber;
	@Column(columnDefinition = "varchar(6)")
	private String accountBankIdentification;
	@Column(columnDefinition = "varchar(6)")
	private String branchAccountIdentification;
	@Column(columnDefinition = "varchar(2)")
	private String accountType;
	private String outletNumber;
	private String merchantNumberB;
	private String acronym;
	private Instant settlementDate;
	
}
