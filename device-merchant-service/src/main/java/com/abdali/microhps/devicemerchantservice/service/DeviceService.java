package com.abdali.microhps.devicemerchantservice.service;

import java.util.List;
import java.util.Map;

import com.abdali.microhps.devicemerchantservice.dto.DeviceDto;

public interface DeviceService {
	
	Map<String, Object> findAll(String title, int page, int size, String[] sort);
	
	DeviceDto save(DeviceDto deviceDto);
	
	DeviceDto findByDeviceNumber(String deviceNumber);
	
	DeviceDto findById(Integer id);
}
