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
public class Denomination implements Serializable {
	
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination1;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination2;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination3;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination4;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination5;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination6;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination7;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination8;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination9;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination10;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination11;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination12;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination13;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination14;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination15;

}
