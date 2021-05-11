package com.abdali.microhps.modechecker.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import lombok.Getter; 
 
@Getter
public class Transaction {
	
	@Column(columnDefinition = "char")
	private Character indicator;
	private String deviceNumber;
	@Column(columnDefinition = "varchar(14)")
	private String bagNumber; 
	@Embedded
	private DropTransaction dropTransaction;
}