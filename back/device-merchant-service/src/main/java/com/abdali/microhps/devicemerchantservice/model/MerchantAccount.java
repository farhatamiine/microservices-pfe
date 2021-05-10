package com.abdali.microhps.devicemerchantservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class MerchantAccount extends AuditEntity {
	
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private int id;
//	private Double min_exchange;
//	private Double max_exchange;
//	
//	@OneToOne(mappedBy = "merchantAccount")
//	private Merchant merchant;
	
	private String accountNumber;
	
	@Column(name="account_type")
	private AccountTypeEnum accountType;
	
	@ManyToOne
	@JoinColumn(name="idmerchant")
	private Merchant merchants;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "merchant_limits_id", referencedColumnName = "id")
	private AccountLimits accountLimits;
	
}
