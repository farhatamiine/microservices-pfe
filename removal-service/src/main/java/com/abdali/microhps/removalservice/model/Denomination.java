package com.abdali.microhps.removalservice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
@Builder
public class Denomination implements Serializable {
	
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination1 = 0;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination2 = 0;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination3 = 0;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination4 = 0;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination5 = 0;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination6 = 0;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination7 = 0;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination8 = 0;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination9 = 0;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination10 = 0;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination11 = 0;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination12 = 0;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination13 = 0;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination14 = 0;
	@Column(columnDefinition = "smallint default 0")
	private Integer denomination15 = 0;

}
