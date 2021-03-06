package com.abdali.microhps.verificationadjustmentservice.model;

import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoreTransactionModel extends AuditEntity {
	
	private Integer transactionId;
	@Column(columnDefinition = "varchar(15)")
	private String deviceNumber;
	@Column(columnDefinition = "varchar(14)")
	private String bagNumber; 
	private Instant transmitionDate;  
	private BigDecimal totalAmount;
	@Column(columnDefinition = "varchar(3)")
	private String currency;
	private String merchantSettlementMode;
	private Long merchantNumber;
	
	// credited/debited.
	private String typeCD; 
}