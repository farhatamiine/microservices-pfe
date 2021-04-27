package com.abdali.microhps.integrityservice.model;

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
public class RemovalDropTransaction implements Serializable {
	
	@Column(length=20)
	private String depositReference;
	
	@Column(columnDefinition = "smallint")
	private Integer sequenceNumber;
}
