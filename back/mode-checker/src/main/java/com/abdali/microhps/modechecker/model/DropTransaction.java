package com.abdali.microhps.modechecker.model;

import java.io.Serializable;

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
public class DropTransaction implements Serializable {
    private Long merchantNumber;
}