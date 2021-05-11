package com.abdali.microhps.removaladjustmentservice.model;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;
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
@Table(name="REMOVAL_ADJUSTEMENT_EVENTS")
public class AdjustementEvent extends AuditEntity {
	
	private Long merchantNumber;
	private Integer transactionId;
	private Instant transmitionDate;
	private BigDecimal creaditedAmount;
	private BigDecimal debitedAmount;
	private String AcountNumber;
	
}
