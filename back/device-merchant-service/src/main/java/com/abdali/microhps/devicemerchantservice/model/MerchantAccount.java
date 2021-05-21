package com.abdali.microhps.devicemerchantservice.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.abdali.microhps.devicemerchantservice.model.enumeration.AccountTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "merchant_account")
public class MerchantAccount extends AuditEntity {
	
	private String accountNumber;
	
	@Column(name="account_type")
	private AccountTypeEnum accountType;

	private BigDecimal virtualAmount;
	
	@ManyToOne
	@JoinColumn(name="idmerchant")
	private Merchant merchants;
	
}
