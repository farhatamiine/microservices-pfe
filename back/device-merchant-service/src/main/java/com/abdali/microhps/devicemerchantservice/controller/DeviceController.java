package com.abdali.microhps.devicemerchantservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abdali.microhps.devicemerchantservice.dto.DeviceDto;
import com.abdali.microhps.devicemerchantservice.service.DeviceService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin("*")
public class DeviceController { 
	
	private DeviceService deviceService;
	
	@Autowired
	  public DeviceController(
			  DeviceService deviceService
	  ) {
	    this.deviceService = deviceService;
	  }
	
	@GetMapping("/merchant-device/devices")
    public ResponseEntity<Map<String, Object>> getAllDevices(
    	  @RequestParam(required = false) String deviceNumber,
	      @RequestParam(defaultValue = "0") int page,
	      @RequestParam(defaultValue = "10") int size) {

	   try {
	      Map<String, Object> result = deviceService.findAll(deviceNumber, page, size);

	      if(result.get("devices").toString() == "[]" ) {
	    	  return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);  
	      }	      
	      return new ResponseEntity<>(result, HttpStatus.OK);
	      
	    } catch (Exception e) {
	    	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

    }   
    
    @PostMapping("/merchant-device/devices")
    public DeviceDto addDevice(@RequestBody DeviceDto deviceDto) {
        return deviceService.save(deviceDto);
    }
    
    @GetMapping("/merchant-device/devices/{deviceId}")
    public DeviceDto getDeviceById(@PathVariable("deviceId") String deviceNumber) {
        return deviceService.findByDeviceNumber(deviceNumber);
    }

	@GetMapping("/merchant-device/device-exist/{deviceNumber}")
	public Boolean isDeviceNumberExist(@PathVariable("deviceNumber") String deviceNumber) throws Exception {
		return deviceService.isDevicenNumberExist(deviceNumber);
	}

}
