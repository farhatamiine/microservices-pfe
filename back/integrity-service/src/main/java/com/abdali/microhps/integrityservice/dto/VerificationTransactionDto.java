package com.abdali.microhps.integrityservice.dto;

import com.abdali.microhps.integrityservice.model.VerificationTransaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationTransactionDto {
	
	private String cashCenterType;
	private String cashCenterCode;
	
	public static VerificationTransactionDto fromEntity(VerificationTransaction verificationTransaction) {
		if(verificationTransaction == null)
			return null;
		
		return VerificationTransactionDto.builder()
		.cashCenterCode(verificationTransaction.getCashCenterCode())
		.cashCenterType(verificationTransaction.getCashCenterType())
		.build();
	}
	
	public static VerificationTransaction toEntity(VerificationTransactionDto verificationTransactionDto) {
		if(verificationTransactionDto == null)
			return null;
		
		VerificationTransaction verificationTransaction = new VerificationTransaction();
		verificationTransaction.setCashCenterCode(verificationTransactionDto.getCashCenterCode());
		verificationTransaction.setCashCenterType(verificationTransactionDto.getCashCenterType());
		
		return verificationTransaction;
	}
}