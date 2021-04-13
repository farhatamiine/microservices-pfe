package com.abdali.microhps.devicemerchantservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abdali.microhps.devicemerchantservice.model.Device;

public interface DeviceRepository extends JpaRepository<Device, Integer>{
	Optional<Device> findDeviceByDeviceNumber(Integer Id);
}
