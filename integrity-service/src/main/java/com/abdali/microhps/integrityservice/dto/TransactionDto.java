package com.abdali.microhps.integrityservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Embedded;

import com.abdali.microhps.integrityservice.model.Denomination;
import com.abdali.microhps.integrityservice.model.DropTransaction;
import com.abdali.microhps.integrityservice.model.RemovalDropTransaction;
import com.abdali.microhps.integrityservice.model.Transaction;
import com.abdali.microhps.integrityservice.model.VerificationTransaction; 

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionDto {
	
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
	
	public static TransactionDto fromEntity(Transaction transaction) {
		if(transaction ==null) {
			return null;
		}
		return TransactionDto.builder()
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
	
	public static Transaction toEntity(TransactionDto transactionDto) {
		if(transactionDto == null) {
			return null;
		}
		
		Transaction transaction = new Transaction();
		transaction.setIndicator(transactionDto.getIndicator());
		transaction.setTransactionId(transactionDto.getTransactionId()); 
		transaction.setDeviceNumber(transactionDto.getDeviceNumber());
		transaction.setBagNumber(transactionDto.getBagNumber());
		transaction.setContainerType(transactionDto.getContainerType());	
		transaction.setTransmitionDate(transactionDto.getTransmitionDate());
		transaction.setTotalCoins(transactionDto.getTotalCoins());
		transaction.setTotalNotes(transactionDto.getTotalNotes());
		transaction.setTotalAmount(transactionDto.getTotalAmount());
		transaction.setCurrency(transactionDto.getCurrency());
		transaction.setCanisterNumber(transactionDto.getCanisterNumber());
		transaction.setSettlementFlag(transactionDto.getSettlementFlag());
		transaction.setDenomination(DenominationDto.toEntity(transactionDto.getDenomination()));
		transaction.setDropTransaction(DropTransactionDto.toEntity(transactionDto.getDropTransaction()));
		transaction.setRemovalDropTransaction(RemovalDropTransactionDto.toEntity(transactionDto.getRemovalDropTransaction()));
		transaction.setVerificationTransaction(VerificationTransactionDto.toEntity(transactionDto.getVerificationTransaction()));
		return transaction;
		
 	}
}
