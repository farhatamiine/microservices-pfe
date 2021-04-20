package com.abdali.microhps.verificationservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.abdali.microhps.verificationservice.model.VerificationMessage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationMessageDto {
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
	
	public static VerificationMessageDto fromEntity(VerificationMessage verificationMessage) {
		if(verificationMessage == null)
			return null;
		
		return VerificationMessageDto.builder()
				.id(verificationMessage.getId())
				.indicator(verificationMessage.getIndicator())
				.transactionId(verificationMessage.getTransactionId())
				.deviceNumber(verificationMessage.getDeviceNumber())
				.bagNumber(verificationMessage.getBagNumber())
				.containerType(verificationMessage.getContainerType())
				.transmitionDate(verificationMessage.getTransmitionDate())
				.depositReference(verificationMessage.getDepositReference())
				.sequenceNumber(verificationMessage.getSequenceNumber())
				.merchantNumber(verificationMessage.getMerchantNumber())
				.totalCoins(verificationMessage.getTotalCoins())
				.totalNotes(verificationMessage.getTotalNotes())
				.totalAmount(verificationMessage.getTotalAmount())
				.currency(verificationMessage.getCurrency())
				.canisterNumber(verificationMessage.getCanisterNumber())
				.denomination(DenominationDto.fromEntity(verificationMessage.getDenomination()))
				.build();
	}
	
	public static VerificationMessage toEntity(VerificationMessageDto verificationMessageDto) {
		if(verificationMessageDto == null)
			return null;
		
		VerificationMessage verificationMessage = new VerificationMessage();
		verificationMessage.setIndicator(verificationMessageDto.getIndicator());
		verificationMessage.setTransactionId(verificationMessageDto.getTransactionId());
		verificationMessage.setDeviceNumber(verificationMessageDto.getDeviceNumber());
		verificationMessage.setBagNumber(verificationMessageDto.getBagNumber());
		verificationMessage.setContainerType(verificationMessageDto.getContainerType());
		verificationMessage.setTransmitionDate(verificationMessageDto.getTransmitionDate());
		verificationMessage.setDepositReference(verificationMessageDto.getDepositReference());
		verificationMessage.setSequenceNumber(verificationMessageDto.getSequenceNumber());
		verificationMessage.setMerchantNumber(verificationMessageDto.getMerchantNumber());
		verificationMessage.setTotalCoins(verificationMessageDto.getTotalCoins());
		verificationMessage.setTotalNotes(verificationMessageDto.getTotalNotes());
		verificationMessage.setTotalAmount(verificationMessageDto.getTotalAmount());
		verificationMessage.setCurrency(verificationMessageDto.getCurrency());
		verificationMessage.setCanisterNumber(verificationMessageDto.getCanisterNumber());
		verificationMessage.setDenomination(DenominationDto.toEntity(verificationMessageDto.getDenomination()));
		
		return verificationMessage;
	}
	
}
