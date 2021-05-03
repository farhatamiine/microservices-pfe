package com.abdali.microhps.devicemerchantservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "powerCard_notification")
public class PowerCardNotification extends AuditEntity {
	
	private String notificationTitle;
	private String notificationDescription;
	private String notificationTransaction;
	private Long merchantNumber;
	private Integer transactionId;
	@Column(columnDefinition = "varchar(15)")
	private String deviceNumber;
	@Column(columnDefinition = "smallint")
	private Integer sequenceNumber;
	
}
