package com.abdali.microhps.devicemerchantservice.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditEntity implements Serializable {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@CreatedDate
    @Column(name = "createdDate", nullable = false, updatable = false)
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "updatedDate")
    private Date lastModifiedDate;
}
