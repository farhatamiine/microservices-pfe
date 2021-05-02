package com.abdali.microhps.dropservice.dto;

import com.abdali.microhps.dropservice.model.RemovalDropTransaction;

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