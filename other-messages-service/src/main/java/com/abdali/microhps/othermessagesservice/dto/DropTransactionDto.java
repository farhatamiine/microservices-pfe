package com.abdali.microhps.othermessagesservice.dto;

import com.abdali.microhps.othermessagesservice.model.DropTransaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DropTransactionDto {
	
	private Long merchantNumber;
	
	public static DropTransactionDto fromEntity(DropTransaction dropTransaction) {
		if(dropTransaction == null) {
			return null;
		}
		
		return DropTransactionDto.builder()
				.merchantNumber(dropTransaction.getMerchantNumber())
				.build();
	}
	
	public static DropTransaction toEntity(DropTransactionDto dropTransactionDto) {
		if(dropTransactionDto == null) {
			return null;
		}
		DropTransaction dropTransaction = new DropTransaction();
		dropTransaction.setMerchantNumber(dropTransactionDto.getMerchantNumber());
		return dropTransaction;
	}
}