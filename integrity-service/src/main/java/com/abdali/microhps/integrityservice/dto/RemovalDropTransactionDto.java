package com.abdali.microhps.integrityservice.dto;

import javax.persistence.Column;

import com.abdali.microhps.integrityservice.model.RemovalDropTransaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemovalDropTransactionDto { 
	
	private String depositReference;
	 
	private Integer sequenceNumber;
	
	public static RemovalDropTransactionDto fromEntity(RemovalDropTransaction removalDropTransaction) {
		if(removalDropTransaction == null)
			return null;
		
		return RemovalDropTransactionDto.builder()
				.depositReference(removalDropTransaction.getDepositReference())
				.sequenceNumber(removalDropTransaction.getSequenceNumber())
				.build();
	}
	
	public static RemovalDropTransaction toEntity(RemovalDropTransactionDto removalDropTransactionDto) {
		if(removalDropTransactionDto == null)
			return null;
		
		RemovalDropTransaction removalDropTransaction = new RemovalDropTransaction();
		removalDropTransaction.setDepositReference(removalDropTransactionDto.getDepositReference());
		removalDropTransaction.setSequenceNumber(removalDropTransactionDto.getSequenceNumber());
		return removalDropTransaction;
	}
}
