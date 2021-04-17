package com.abdali.microhps.devicemerchantservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abdali.microhps.devicemerchantservice.dto.DeviceDto;
import com.abdali.microhps.devicemerchantservice.service.DeviceService;
  

@RestController
public class DeviceController { 
	
	private DeviceService deviceService;
	
	@Autowired
	  public DeviceController(
			  DeviceService deviceService
	  ) {
	    this.deviceService = deviceService;
	  }
	
	@GetMapping("/devices")
    public List<DeviceDto> getAllPosts() {
        return deviceService.findAll();
    }

    @PostMapping("/devices")
    public DeviceDto addDevice(@RequestBody DeviceDto deviceDto) {
        return deviceService.save(deviceDto);
    }
    
    @GetMapping("/devices/{deviceId}")
    public DeviceDto getDeviceById(@PathVariable("deviceId") String deviceNumber) {
        return deviceService.findByDeviceNumber(deviceNumber);
    }
}
