package com.abdali.microhps.dropservice.dto;

import javax.persistence.Transient;

import com.abdali.microhps.dropservice.model.DropTransaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DropTransactionDto {
	
	@Transient
	private String dropMessage;
	
	private Long merchantNumber;
	
	public static DropTransactionDto fromEntity(DropTransaction dropTransaction) {
		if(dropTransaction == null) {
			return null;
		}
		
		return DropTransactionDto.builder()
				.merchantNumber(dropTransaction.getMerchantNumber())
				.dropMessage(dropTransaction.getDropMessage())
				.build();
	}
	
	public static DropTransaction toEntity(DropTransactionDto dropTransactionDto) {
		if(dropTransactionDto == null) {
			return null;
		}
		DropTransaction dropTransaction = new DropTransaction();
		dropTransaction.setMerchantNumber(dropTransactionDto.getMerchantNumber());
		dropTransaction.setDropMessage(dropTransaction.getDropMessage());
		return dropTransaction;
	}
}