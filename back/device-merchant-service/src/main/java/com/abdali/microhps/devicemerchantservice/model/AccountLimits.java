package com.abdali.microhps.devicemerchantservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "merchant_account")
public class AccountLimits {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private Double minExchange;
	private Double maxExchange;
	
	@OneToOne(mappedBy = "accountLimits")
	private MerchantAccount merchantAccount;
}