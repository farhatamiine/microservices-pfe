package com.abdali.microhps.removalservice.model;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="SMB_DROP_MESSAGES")
public class DropMessage extends AuditEntity {

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
	private Long merchantNumber;
	@Column(length=20)
	private String depositReference;
	@Column(columnDefinition = "smallint")
	private Integer sequenceNumber;
	private Integer totalCoins;
	private Integer totalNotes;
	private BigDecimal totalAmount;
	@Column(columnDefinition = "varchar(3)")
	private String currency;
	@Column(columnDefinition = "int")
	private Integer canisterNumber;
	
	@Embedded
	private Denomination denomination;
}
