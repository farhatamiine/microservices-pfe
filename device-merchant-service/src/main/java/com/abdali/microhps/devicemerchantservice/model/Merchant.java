package com.abdali.microhps.devicemerchantservice.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode; 
import lombok.NoArgsConstructor; 

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "merchant")
public class Merchant extends AuditEntity {
	
	private Long merchantNumber;
	
	private String merchantName;
	
	@Column(name="status")
	private MerchantStatus status;

	@ManyToMany
	@JoinTable(
			  name = "mechant_device", 
			  joinColumns = @JoinColumn(name = "merchant_id"), 
			  inverseJoinColumns = @JoinColumn(name = "device_id"))
	private List<Device> devices;
}
