package com.abdali.microhps.othermessagesservice.model;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Embedded;
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
@Table(name = "SMB_OTHERS_TABLE")
public class OtherTransaction extends AuditEntity {
		
	private Integer transactionId;
	@Column(columnDefinition = "varchar(15)")
	private String deviceNumber;
	@Column(columnDefinition = "varchar(14)")
	private String bagNumber; 
	@Column(columnDefinition = "char")
	private Character containerType;
	private Instant transmitionDate;
	private Character settlementFlag;
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
	private DropTransaction dropTransaction;
	
	@Embedded
	private RemovalDropTransaction removalDropTransaction;
	
	@Embedded
	private VerificationTransaction verificationTransaction;

	private String message;

}