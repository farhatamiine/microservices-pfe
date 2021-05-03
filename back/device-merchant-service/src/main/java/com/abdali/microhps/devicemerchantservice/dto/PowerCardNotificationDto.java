package com.abdali.microhps.devicemerchantservice.dto;

import com.abdali.microhps.devicemerchantservice.model.PowerCardNotification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PowerCardNotificationDto {
	
	private Integer id;
	private String notificationTitle;
	private String notificationDescription;
	private String notificationTransaction;
	private Long merchantNumber;
	private Integer transactionId; 
	private String deviceNumber; 
	private Integer sequenceNumber;
	
	public static PowerCardNotificationDto fromEntity(PowerCardNotification powerCardNotification) {
		if(powerCardNotification == null)
			return null;
					
		return PowerCardNotificationDto.builder()
		.id(powerCardNotification.getId())
		.notificationTitle(powerCardNotification.getNotificationTitle())
		.notificationDescription(powerCardNotification.getNotificationDescription())
		.notificationTransaction(powerCardNotification.getNotificationDescription())
		.merchantNumber(powerCardNotification.getMerchantNumber())
		.transactionId(powerCardNotification.getTransactionId())
		.sequenceNumber(powerCardNotification.getSequenceNumber())
		.build();
	}
	
	public static PowerCardNotification toEntity(PowerCardNotificationDto powerCardNotificationDto) {
		if(powerCardNotificationDto == null)
			return null;
		
		PowerCardNotification powerCardNotification = new PowerCardNotification();
		powerCardNotification.setId(powerCardNotificationDto.getId());
		powerCardNotification.setNotificationTitle(powerCardNotificationDto.getNotificationTitle());
		powerCardNotification.setNotificationDescription(powerCardNotificationDto.getNotificationDescription());
		powerCardNotification.setNotificationTransaction(powerCardNotificationDto.getNotificationTransaction());
		powerCardNotification.setMerchantNumber(powerCardNotificationDto.getMerchantNumber());
		powerCardNotification.setTransactionId(powerCardNotificationDto.getTransactionId());
		powerCardNotification.setDeviceNumber(powerCardNotificationDto.getDeviceNumber());
		powerCardNotification.setSequenceNumber(powerCardNotificationDto.getSequenceNumber());
		
		return powerCardNotification;
	}
}
