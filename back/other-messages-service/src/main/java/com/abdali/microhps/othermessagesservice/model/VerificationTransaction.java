package com.abdali.microhps.othermessagesservice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class VerificationTransaction implements Serializable {
	
	@Column(columnDefinition = "varchar(4)")
	private String cashCenterType;
	@Column(columnDefinition = "varchar(4)")
	private String cashCenterCode;

}