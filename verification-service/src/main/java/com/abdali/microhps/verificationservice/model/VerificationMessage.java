package com.abdali.microhps.verificationservice.model;

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
@Table(name="SMB_VERIFICATION_MESSAGES")
public class VerificationMessage extends AuditEntity {

	@Column(columnDefinition = "char")
	private Character indicator;
	@Column(unique=false)
	private Integer transactionId;
	@Column(columnDefinition = "varchar(15)")
	private String deviceNumber;
	@Column(columnDefinition = "varchar(14)")
	private String bagNumber; 
	@Column(columnDefinition = "char")
	private Character containerType;
	private Instant transmitionDate; 
	@Column(columnDefinition = "default 0")
	private Character settlementFlag;
	@Column(length=20)
	private String depositReference;
	private Integer totalCoins;
	private Integer totalNotes;
	private BigDecimal totalAmount;
	@Column(columnDefinition = "varchar(3)")
	private String currency;
	@Column(columnDefinition = "int")
	private Integer canisterNumber;
	
	@Column(columnDefinition = "varchar(4)")
	private String cashCenterType;
	@Column(columnDefinition = "varchar(4)")
	private String cashCenterCode;
	
	@Embedded
	private Denomination denomination;
}
