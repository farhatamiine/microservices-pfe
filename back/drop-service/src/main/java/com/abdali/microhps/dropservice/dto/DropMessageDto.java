package com.abdali.microhps.dropservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;

import com.abdali.microhps.dropservice.model.DropMessage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DropMessageDto {
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
	private String settlementFlag;
	
	public static DropMessageDto fromEntity(DropMessage dropMessage) {
		if(dropMessage == null)
			return null;
		
		return DropMessageDto.builder()
				.id(dropMessage.getId())
				.indicator(dropMessage.getIndicator())
				.transactionId(dropMessage.getTransactionId())
				.deviceNumber(dropMessage.getDeviceNumber())
				.bagNumber(dropMessage.getBagNumber())
				.containerType(dropMessage.getContainerType())
				.transmitionDate(dropMessage.getTransmitionDate())
				.depositReference(dropMessage.getDepositReference())
				.sequenceNumber(dropMessage.getSequenceNumber())
				.merchantNumber(dropMessage.getMerchantNumber())
				.totalCoins(dropMessage.getTotalCoins())
				.totalNotes(dropMessage.getTotalNotes())
				.totalAmount(dropMessage.getTotalAmount())
				.currency(dropMessage.getCurrency())
				.settlementFlag(dropMessage.getSettlementFlag())
				.canisterNumber(dropMessage.getCanisterNumber())
				.denomination(DenominationDto.fromEntity(dropMessage.getDenomination()))
				.build();
	}
	
	public static DropMessage toEntity(DropMessageDto dropMessageDto) {
		if(dropMessageDto == null)
			return null;
		
		DropMessage dropMessage = new DropMessage();
		dropMessage.setIndicator(dropMessageDto.getIndicator());
		dropMessage.setTransactionId(dropMessageDto.getTransactionId());
		dropMessage.setDeviceNumber(dropMessageDto.getDeviceNumber());
		dropMessage.setBagNumber(dropMessageDto.getBagNumber());
		dropMessage.setContainerType(dropMessageDto.getContainerType());
		dropMessage.setTransmitionDate(dropMessageDto.getTransmitionDate());
		dropMessage.setDepositReference(dropMessageDto.getDepositReference());
		dropMessage.setSequenceNumber(dropMessageDto.getSequenceNumber());
		dropMessage.setMerchantNumber(dropMessageDto.getMerchantNumber());
		dropMessage.setTotalCoins(dropMessageDto.getTotalCoins());
		dropMessage.setTotalNotes(dropMessageDto.getTotalNotes());
		dropMessage.setTotalAmount(dropMessageDto.getTotalAmount());
		dropMessage.setCurrency(dropMessageDto.getCurrency());
		dropMessage.setSettlementFlag(dropMessageDto.getSettlementFlag());
		dropMessage.setCanisterNumber(dropMessageDto.getCanisterNumber());
		dropMessage.setDenomination(DenominationDto.toEntity(dropMessageDto.getDenomination()));
		
		return dropMessage;
	}
	
}
