package com.abdali.microhps.devicemerchantservice.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
@Table(name = "device")
public class Device extends AuditEntity {

	private String deviceNumber;

	@OneToMany(mappedBy = "device")
	private List<Merchant> merchants;
}
