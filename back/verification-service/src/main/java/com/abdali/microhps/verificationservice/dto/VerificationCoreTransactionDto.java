package com.abdali.microhps.verificationservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.abdali.microhps.verificationservice.model.VerificationCoreTransaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationCoreTransactionDto {
	
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
	private VerificationTransactionDto verificationTransaction;
	
	public static VerificationCoreTransactionDto fromEntity(VerificationCoreTransaction verificationCoreTransaction) {
		if(verificationCoreTransaction == null)
			return null;
		
		return VerificationCoreTransactionDto.builder()
				.id(verificationCoreTransaction.getId())
				.indicator(verificationCoreTransaction.getIndicator())
				.transactionId(verificationCoreTransaction.getTransactionId())
				.deviceNumber(verificationCoreTransaction.getDeviceNumber())
				.bagNumber(verificationCoreTransaction.getBagNumber())
				.containerType(verificationCoreTransaction.getContainerType())
				.transmitionDate(verificationCoreTransaction.getTransmitionDate())
				.totalCoins(verificationCoreTransaction.getTotalCoins())
				.totalNotes(verificationCoreTransaction.getTotalNotes())
				.totalAmount(verificationCoreTransaction.getTotalAmount())
				.currency(verificationCoreTransaction.getCurrency())
				.settlementFlag(verificationCoreTransaction.getSettlementFlag())
				.canisterNumber(verificationCoreTransaction.getCanisterNumber())
				.denomination(DenominationDto.fromEntity(verificationCoreTransaction.getDenomination()))
				.verificationTransaction(VerificationTransactionDto.fromEntity(verificationCoreTransaction.getVerificationTransaction()))
				.build();
	}
	
	public static VerificationCoreTransaction toEntity(VerificationCoreTransactionDto verificationCoreTransactionDto) {
		if(verificationCoreTransactionDto == null)
			return null;
		
		VerificationCoreTransaction verificationMessage = new VerificationCoreTransaction();
		verificationMessage.setIndicator(verificationCoreTransactionDto.getIndicator());
		verificationMessage.setTransactionId(verificationCoreTransactionDto.getTransactionId());
		verificationMessage.setDeviceNumber(verificationCoreTransactionDto.getDeviceNumber());
		verificationMessage.setBagNumber(verificationCoreTransactionDto.getBagNumber());
		verificationMessage.setContainerType(verificationCoreTransactionDto.getContainerType());
		verificationMessage.setTransmitionDate(verificationCoreTransactionDto.getTransmitionDate());
		verificationMessage.setTotalCoins(verificationCoreTransactionDto.getTotalCoins());
		verificationMessage.setTotalNotes(verificationCoreTransactionDto.getTotalNotes());
		verificationMessage.setTotalAmount(verificationCoreTransactionDto.getTotalAmount());
		verificationMessage.setSettlementFlag(verificationCoreTransactionDto.getSettlementFlag());
		verificationMessage.setCurrency(verificationCoreTransactionDto.getCurrency());
		verificationMessage.setCanisterNumber(verificationCoreTransactionDto.getCanisterNumber());
		verificationMessage.setDenomination(DenominationDto.toEntity(verificationCoreTransactionDto.getDenomination()));
		verificationMessage.setVerificationTransaction(VerificationTransactionDto.toEntity(verificationCoreTransactionDto.getVerificationTransaction()));
		return verificationMessage;
	}
	
}
