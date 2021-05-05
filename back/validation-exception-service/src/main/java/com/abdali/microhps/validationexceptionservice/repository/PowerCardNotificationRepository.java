package com.abdali.microhps.validationexceptionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abdali.microhps.validationexceptionservice.model.PowerCardNotification;

public interface PowerCardNotificationRepository extends JpaRepository<PowerCardNotification, Integer>  {

}
