package com.abdali.microhps.dropservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.abdali.microhps.dropservice.model.DropCoreTransaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DropCoreTransactionDto {

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
	private String depositReference;
	private Integer sequenceNumber;
	private String settlementFlag;
	private DenominationDto denomination;
	private RemovalDropTransactionDto removalDropTransaction;
	private DropTransactionDto dropTransaction;
	
	public static DropCoreTransactionDto fromEntity(DropCoreTransaction dropCoreTransaction) {
		if(dropCoreTransaction == null)
			return null;
		
		return DropCoreTransactionDto.builder()
				.id(dropCoreTransaction.getId())
				.indicator(dropCoreTransaction.getIndicator())
				.transactionId(dropCoreTransaction.getTransactionId())
				.deviceNumber(dropCoreTransaction.getDeviceNumber())
				.bagNumber(dropCoreTransaction.getBagNumber())
				.containerType(dropCoreTransaction.getContainerType())
				.transmitionDate(dropCoreTransaction.getTransmitionDate())
				.totalCoins(dropCoreTransaction.getTotalCoins())
				.totalNotes(dropCoreTransaction.getTotalNotes())
				.totalAmount(dropCoreTransaction.getTotalAmount())
				.currency(dropCoreTransaction.getCurrency())
				.settlementFlag(dropCoreTransaction.getSettlementFlag())
				.canisterNumber(dropCoreTransaction.getCanisterNumber())
				.denomination(DenominationDto.fromEntity(dropCoreTransaction.getDenomination()))
				.dropTransaction(DropTransactionDto.fromEntity(dropCoreTransaction.getDropTransaction()))
				.removalDropTransaction(RemovalDropTransactionDto.fromEntity(dropCoreTransaction.getRemovalDropTransaction()))
				.build();
	}
	
	public static DropCoreTransaction toEntity(DropCoreTransactionDto dropCoreTransactionDto) {
		if(dropCoreTransactionDto == null)
			return null;
		
		DropCoreTransaction dropCoreTransaction = new DropCoreTransaction();
		dropCoreTransaction.setIndicator(dropCoreTransactionDto.getIndicator());
		dropCoreTransaction.setTransactionId(dropCoreTransactionDto.getTransactionId());
		dropCoreTransaction.setDeviceNumber(dropCoreTransactionDto.getDeviceNumber());
		dropCoreTransaction.setBagNumber(dropCoreTransactionDto.getBagNumber());
		dropCoreTransaction.setContainerType(dropCoreTransactionDto.getContainerType());
		dropCoreTransaction.setTransmitionDate(dropCoreTransactionDto.getTransmitionDate());
		dropCoreTransaction.setTotalCoins(dropCoreTransactionDto.getTotalCoins());
		dropCoreTransaction.setTotalNotes(dropCoreTransactionDto.getTotalNotes());
		dropCoreTransaction.setTotalAmount(dropCoreTransactionDto.getTotalAmount());
		dropCoreTransaction.setCurrency(dropCoreTransactionDto.getCurrency());
		dropCoreTransaction.setSettlementFlag(dropCoreTransactionDto.getSettlementFlag());
		dropCoreTransaction.setCanisterNumber(dropCoreTransactionDto.getCanisterNumber());
		dropCoreTransaction.setDenomination(DenominationDto.toEntity(dropCoreTransactionDto.getDenomination()));
		dropCoreTransaction.setDropTransaction(DropTransactionDto.toEntity(dropCoreTransactionDto.getDropTransaction()));
		dropCoreTransaction.setRemovalDropTransaction(RemovalDropTransactionDto.toEntity(dropCoreTransactionDto.getRemovalDropTransaction()));
		
		return dropCoreTransaction;
	}
	
}
