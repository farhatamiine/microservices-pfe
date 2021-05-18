package com.abdali.microhps.modechecker.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Embedded;
import lombok.Getter; 
 
@Getter
public class Transaction {

	private Integer transactionId;
	@Column(columnDefinition = "char")
	private Character indicator;
	private String deviceNumber;
	@Column(columnDefinition = "varchar(14)")
	private String bagNumber; 
	private Instant transmitionDate;
	@Embedded
	private DropTransaction dropTransaction;
}