package com.abdali.microhps.devicemerchantservice.service;

import java.util.Map;

import com.abdali.microhps.devicemerchantservice.dto.DeviceDto;

public interface DeviceService {
	
	Map<String, Object> findAll(String title, int page, int size);
	
	DeviceDto save(DeviceDto deviceDto);
	
	DeviceDto findByDeviceNumber(String deviceNumber);
	
	DeviceDto findById(Integer id);
	
	Boolean isDevicenNumberExist(String deviceNumber);
}
