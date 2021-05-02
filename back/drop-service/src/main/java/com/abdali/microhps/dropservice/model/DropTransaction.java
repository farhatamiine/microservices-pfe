package com.abdali.microhps.dropservice.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class DropTransaction implements Serializable {
	@Transient
	private String dropMessage;
    private Long merchantNumber;
}
