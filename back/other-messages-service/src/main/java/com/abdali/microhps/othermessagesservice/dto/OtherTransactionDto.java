package com.abdali.microhps.othermessagesservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.abdali.microhps.othermessagesservice.model.OtherTransaction;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OtherTransactionDto {
	
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
	private Character settlementFlag;
	private DenominationDto denomination; 
	private DropTransactionDto dropTransaction; 
	private RemovalDropTransactionDto removalDropTransaction; 
	private VerificationTransactionDto verificationTransaction;
	
	public static OtherTransactionDto fromEntity(OtherTransaction transaction) {
		if(transaction ==null) {
			return null;
		}
		return OtherTransactionDto.builder()
				.id(transaction.getId())
				.indicator(transaction.getIndicator()) 
				.transactionId(transaction.getTransactionId())
				.deviceNumber(transaction.getDeviceNumber())
				.bagNumber(transaction.getBagNumber())
				.containerType(transaction.getContainerType()) 
				.transmitionDate(transaction.getTransmitionDate())
				.totalCoins(transaction.getTotalCoins())
				.totalNotes(transaction.getTotalNotes())
				.totalAmount(transaction.getTotalAmount())
				.currency(transaction.getCurrency())
				.canisterNumber(transaction.getCanisterNumber())
				.settlementFlag(transaction.getSettlementFlag())
				.denomination(DenominationDto.fromEntity(transaction.getDenomination()))
				.dropTransaction(DropTransactionDto.fromEntity(transaction.getDropTransaction()))
				.removalDropTransaction(RemovalDropTransactionDto.fromEntity(transaction.getRemovalDropTransaction()))
				.verificationTransaction(VerificationTransactionDto.fromEntity(transaction.getVerificationTransaction()))
				.build();
	}
	
	public static OtherTransaction toEntity(OtherTransactionDto otherTransactionDto) {
		if(otherTransactionDto == null) {
			return null;
		}
		
		OtherTransaction transaction = new OtherTransaction();
		transaction.setIndicator(otherTransactionDto.getIndicator());
		transaction.setTransactionId(otherTransactionDto.getTransactionId()); 
		transaction.setDeviceNumber(otherTransactionDto.getDeviceNumber());
		transaction.setBagNumber(otherTransactionDto.getBagNumber());
		transaction.setContainerType(otherTransactionDto.getContainerType());	
		transaction.setTransmitionDate(otherTransactionDto.getTransmitionDate());
		transaction.setTotalCoins(otherTransactionDto.getTotalCoins());
		transaction.setTotalNotes(otherTransactionDto.getTotalNotes());
		transaction.setTotalAmount(otherTransactionDto.getTotalAmount());
		transaction.setCurrency(otherTransactionDto.getCurrency());
		transaction.setCanisterNumber(otherTransactionDto.getCanisterNumber());
		transaction.setSettlementFlag(otherTransactionDto.getSettlementFlag());
		transaction.setDenomination(DenominationDto.toEntity(otherTransactionDto.getDenomination()));
		transaction.setDropTransaction(DropTransactionDto.toEntity(otherTransactionDto.getDropTransaction()));
		transaction.setRemovalDropTransaction(RemovalDropTransactionDto.toEntity(otherTransactionDto.getRemovalDropTransaction()));
		transaction.setVerificationTransaction(VerificationTransactionDto.toEntity(otherTransactionDto.getVerificationTransaction()));
		return transaction;
		
 	}
}
