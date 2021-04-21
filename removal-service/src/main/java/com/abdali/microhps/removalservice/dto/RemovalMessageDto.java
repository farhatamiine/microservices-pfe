package com.abdali.microhps.removalservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;

import com.abdali.microhps.removalservice.model.RemovalMessage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemovalMessageDto {
	private Long id;
	private Character indicator;
	private Integer transactionId;
	private String deviceNumber;
	private String bagNumber; 
	private Character containerType;
	private Instant transmitionDate; 
	private Long merchantNumber;
	private Integer totalCoins;
	private Integer totalNotes;
	private BigDecimal totalAmount;
	private String currency;
	private Integer canisterNumber;
	private DenominationDto denomination;
	private String depositReference;
	private Integer sequenceNumber;
	private Character settlementFlag;
	
	public static RemovalMessageDto fromEntity(RemovalMessage removalMessage) {
		if(removalMessage == null)
			return null;
		
		return RemovalMessageDto.builder()
				.id(removalMessage.getId())
				.indicator(removalMessage.getIndicator())
				.transactionId(removalMessage.getTransactionId())
				.deviceNumber(removalMessage.getDeviceNumber())
				.bagNumber(removalMessage.getBagNumber())
				.containerType(removalMessage.getContainerType())
				.transmitionDate(removalMessage.getTransmitionDate())
				.depositReference(removalMessage.getDepositReference())
				.sequenceNumber(removalMessage.getSequenceNumber())
				.totalCoins(removalMessage.getTotalCoins())
				.totalNotes(removalMessage.getTotalNotes())
				.totalAmount(removalMessage.getTotalAmount())
				.currency(removalMessage.getCurrency())
				.settlementFlag(removalMessage.getSettlementFlag())
				.canisterNumber(removalMessage.getCanisterNumber())
				.denomination(DenominationDto.fromEntity(removalMessage.getDenomination()))
				.build();
	}
	
	public static RemovalMessage toEntity(RemovalMessageDto removalMessageDto) {
		if(removalMessageDto == null)
			return null;
		
		RemovalMessage removalMessage = new RemovalMessage();
		removalMessage.setIndicator(removalMessageDto.getIndicator());
		removalMessage.setTransactionId(removalMessageDto.getTransactionId());
		removalMessage.setDeviceNumber(removalMessageDto.getDeviceNumber());
		removalMessage.setBagNumber(removalMessageDto.getBagNumber());
		removalMessage.setContainerType(removalMessageDto.getContainerType());
		removalMessage.setTransmitionDate(removalMessageDto.getTransmitionDate());
		removalMessage.setDepositReference(removalMessageDto.getDepositReference());
		removalMessage.setSequenceNumber(removalMessageDto.getSequenceNumber());
		removalMessage.setTotalCoins(removalMessageDto.getTotalCoins());
		removalMessage.setTotalNotes(removalMessageDto.getTotalNotes());
		removalMessage.setTotalAmount(removalMessageDto.getTotalAmount());
		removalMessage.setCurrency(removalMessageDto.getCurrency());
		removalMessage.setSettlementFlag(removalMessageDto.getSettlementFlag());
		removalMessage.setCanisterNumber(removalMessageDto.getCanisterNumber());
		removalMessage.setDenomination(DenominationDto.toEntity(removalMessageDto.getDenomination()));
		
		return removalMessage;
	}
	
}
