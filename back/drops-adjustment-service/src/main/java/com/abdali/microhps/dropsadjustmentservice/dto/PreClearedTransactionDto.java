package com.abdali.microhps.dropsadjustmentservice.dto;

import java.math.BigDecimal;

import com.abdali.microhps.dropsadjustmentservice.model.PreClearedTransaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PreClearedTransactionDto {
	
	private Integer id;
	private String deviceNumber;
	private String bagNumber;  
	private Long merchantNumber;
	private BigDecimal creaditedAmount;
	private BigDecimal debitedAmount;
	private String accountNumber;
	
	public static PreClearedTransactionDto fromEntity(PreClearedTransaction preClearedTransaction) {
		if(preClearedTransaction == null) {
			return null;
		}
		
		return PreClearedTransactionDto.builder()
				.id(preClearedTransaction.getId())
				.deviceNumber(preClearedTransaction.getDeviceNumber())
				.bagNumber(preClearedTransaction.getBagNumber())
				.merchantNumber(preClearedTransaction.getMerchantNumber())
				.creaditedAmount(preClearedTransaction.getCreaditedAmount())
				.debitedAmount(preClearedTransaction.getDebitedAmount())
				.accountNumber(preClearedTransaction.getAccountNumber())
				.build();
		
	}
	
	public static PreClearedTransaction toEntity(PreClearedTransactionDto preClearedTransactionDto) {
		if(preClearedTransactionDto == null) {
			return null;
		}
		
		PreClearedTransaction preClearedTransaction = new PreClearedTransaction(); 
				preClearedTransaction.setId(preClearedTransactionDto.getId());
				preClearedTransaction.setDeviceNumber(preClearedTransactionDto.getDeviceNumber());
				preClearedTransaction.setBagNumber(preClearedTransactionDto.getBagNumber());
				preClearedTransaction.setMerchantNumber(preClearedTransactionDto.getMerchantNumber());
				preClearedTransaction.setCreaditedAmount(preClearedTransactionDto.getCreaditedAmount());
				preClearedTransaction.setDebitedAmount(preClearedTransactionDto.getDebitedAmount());
				preClearedTransaction.setAccountNumber(preClearedTransactionDto.getAccountNumber());
		return preClearedTransaction;
		
	}
}
