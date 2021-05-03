package com.abdali.microhps.removalservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.abdali.microhps.removalservice.model.RemovalCoreMessage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemovalCoreMessageDto {
	
	private Long id;
	private Character indicator;
	private Integer transactionId;
	private String deviceNumber;
	private String bagNumber; 
	private Character containerType;
	private Instant transmitionDate; 
	private Integer totalCoins;
	private Integer totalNotes;
	private BigDecimal totalAmount;
	private String currency;
	private Integer canisterNumber;
	private String settlementFlag;
	private DenominationDto denomination;
	private RemovalDropTransactionDto removalDropTransaction;
	
	public static RemovalCoreMessageDto fromEntity(RemovalCoreMessage removalCoreMessage) {
		if(removalCoreMessage == null)
			return null;
		
		return RemovalCoreMessageDto.builder()
				.id(removalCoreMessage.getId())
				.indicator(removalCoreMessage.getIndicator())
				.transactionId(removalCoreMessage.getTransactionId())
				.deviceNumber(removalCoreMessage.getDeviceNumber())
				.bagNumber(removalCoreMessage.getBagNumber())
				.containerType(removalCoreMessage.getContainerType())
				.transmitionDate(removalCoreMessage.getTransmitionDate())
				.totalCoins(removalCoreMessage.getTotalCoins())
				.totalNotes(removalCoreMessage.getTotalNotes())
				.totalAmount(removalCoreMessage.getTotalAmount())
				.currency(removalCoreMessage.getCurrency())
				.settlementFlag(removalCoreMessage.getSettlementFlag())
				.canisterNumber(removalCoreMessage.getCanisterNumber())
				.denomination(DenominationDto.fromEntity(removalCoreMessage.getDenomination()))
				.removalDropTransaction(RemovalDropTransactionDto.fromEntity(removalCoreMessage.getRemovalDropTransaction()))
				.build();
	}
	
	public static RemovalCoreMessage toEntity(RemovalCoreMessageDto removalCoreMessageDto) {
		if(removalCoreMessageDto == null)
			return null;
		
		RemovalCoreMessage removalMessage = new RemovalCoreMessage();
		removalMessage.setIndicator(removalCoreMessageDto.getIndicator());
		removalMessage.setTransactionId(removalCoreMessageDto.getTransactionId());
		removalMessage.setDeviceNumber(removalCoreMessageDto.getDeviceNumber());
		removalMessage.setBagNumber(removalCoreMessageDto.getBagNumber());
		removalMessage.setContainerType(removalCoreMessageDto.getContainerType());
		removalMessage.setTransmitionDate(removalCoreMessageDto.getTransmitionDate());
		removalMessage.setTotalCoins(removalCoreMessageDto.getTotalCoins());
		removalMessage.setTotalNotes(removalCoreMessageDto.getTotalNotes());
		removalMessage.setTotalAmount(removalCoreMessageDto.getTotalAmount());
		removalMessage.setCurrency(removalCoreMessageDto.getCurrency());
		removalMessage.setSettlementFlag(removalCoreMessageDto.getSettlementFlag());
		removalMessage.setCanisterNumber(removalCoreMessageDto.getCanisterNumber());
		removalMessage.setDenomination(DenominationDto.toEntity(removalCoreMessageDto.getDenomination()));
		removalMessage.setRemovalDropTransaction(RemovalDropTransactionDto.toEntity(removalCoreMessageDto.getRemovalDropTransaction()));
		return removalMessage;
	}
	
}
