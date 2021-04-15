package com.abdali.microhps.integrityservice.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "audit_table")
public class Transaction extends AuditEntity {
	
	@Column(columnDefinition = "char")
	private Character indicator;
	@Column(unique=true)
	private Integer transactionId;
	@Column(columnDefinition = "varchar(15)")
	private String deviceNumber;
	@Column(columnDefinition = "varchar(14)")
	private String bagNumber; 
	@Column(columnDefinition = "char")
	private Character containerType;
	//	The @Temporal annotation tells Hibernate if it should use a java.sql.Date or a java.sql.Timestamp to store the date read from the database. Both extend the java.util.Date class, but java.sql.Date doesn't hold any time information: only the date.
//	@Column(columnDefinition = "DATETIME")
//	@Temporal(TemporalType.TIMESTAMP)
	private Instant transmitionDate; 
	
//	private Character settlementFlag;
	
	private Integer totalCoins;
	private Integer totalNotes;
	private BigDecimal totalAmount;
	@Column(columnDefinition = "varchar(3)")
	private String currency;
	@Column(columnDefinition = "smallint")
	private Integer canisterNumber;
	
	@Embedded
	private Denomination denomination;
	
	@Embedded
	private DropTransaction dropTransaction;
	
	@Embedded
	private RemovalDropTransaction removalDropTransaction;
	
	@Embedded
	private VerificationTransaction verificationTransaction;

}
