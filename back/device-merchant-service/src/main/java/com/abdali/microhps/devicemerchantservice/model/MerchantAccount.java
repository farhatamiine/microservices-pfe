package com.abdali.microhps.devicemerchantservice.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
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
public class MerchantAccount {
	
	@Id
	private int Id;
	private Double min_exchange;
	private Double max_exchange;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Merchant merchant;
	
}
