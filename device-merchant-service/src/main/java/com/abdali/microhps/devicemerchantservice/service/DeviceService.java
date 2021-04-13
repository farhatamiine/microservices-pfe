package com.abdali.microhps.devicemerchantservice.service;

import java.util.List;

import com.abdali.microhps.devicemerchantservice.dto.DeviceDto;


public interface DeviceService {
	
	List<DeviceDto> findAll();
	
	DeviceDto save(DeviceDto deviceDto);
	
	DeviceDto findByDeviceNumber(Integer id);
	
	DeviceDto findById(Integer id);
}
