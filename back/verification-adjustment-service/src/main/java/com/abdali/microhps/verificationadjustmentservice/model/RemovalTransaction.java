package com.abdali.microhps.verificationadjustmentservice.model;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Embedded;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RemovalTransaction extends AuditEntity {
	@Column(columnDefinition = "char")
	private Character indicator;
	private Integer transactionId;
	@Column(columnDefinition = "varchar(15)")
	private String deviceNumber;
	@Column(columnDefinition = "varchar(14)")
	private String bagNumber; 
	@Column(columnDefinition = "char")
	private Character containerType;
	private Instant transmitionDate; 
	@Column(columnDefinition = "varchar(3) default 0")
	private String settlementFlag;
	private Integer totalCoins;
	private Integer totalNotes;
	private BigDecimal totalAmount;
	@Column(columnDefinition = "varchar(3)")
	private String currency;
	@Column(columnDefinition = "int")
	private Integer canisterNumber;
	
	@Embedded
	private Denomination denomination;
	
	@Embedded
	private RemovalDropTransaction removalDropTransaction;
}
