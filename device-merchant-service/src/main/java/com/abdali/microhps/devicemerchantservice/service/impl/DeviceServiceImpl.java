package com.abdali.microhps.devicemerchantservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.devicemerchantservice.dto.DeviceDto;
import com.abdali.microhps.devicemerchantservice.exceptions.types.InvalidEntityException;
import com.abdali.microhps.devicemerchantservice.exceptions.types.NoDataFoundException;
import com.abdali.microhps.devicemerchantservice.repository.DeviceRepository;
import com.abdali.microhps.devicemerchantservice.service.DeviceService;
import com.abdali.microhps.devicemerchantservice.validation.DeviceValidation;

import lombok.extern.slf4j.Slf4j;
 
@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {

	private DeviceRepository deviceRepository;
	
	@Autowired
	public DeviceServiceImpl(DeviceRepository deviceRepository) {
		this.deviceRepository = deviceRepository;
	}
//	- List;
//	- save;
//	- findById;
	
	@Override
	public List<DeviceDto> findAll() {
		return deviceRepository.findAll().stream()
				.map(DeviceDto::fromEntity)
				.collect(Collectors.toList());
	}
	
	@Override
	public DeviceDto save(DeviceDto deviceDto) {
		List<String> errors = DeviceValidation.validate(deviceDto);
		
	    if (!errors.isEmpty()) {
	      log.error("Article is not valid {}", deviceDto);
	      throw new InvalidEntityException("Device n'est pas valide", errors);
	    }
	    
	    // check device exist
	    Optional<DeviceDto> ourDevice = deviceRepository.findDeviceByDeviceNumber(deviceDto.getDeviceNumber()).map(DeviceDto::fromEntity);
		if(ourDevice.isPresent()) {
			throw new NoDataFoundException("Device with The same Number already Exist. Number = " + deviceDto.getDeviceNumber());
		}
		
	    return DeviceDto.fromEntity(
		deviceRepository.save(
				DeviceDto.toEntity(deviceDto)
				)
		);
	}
	
	@Override
	public DeviceDto findByDeviceNumber(Integer id) {
		if (id == null) {
	      return null;
	    }
		return deviceRepository.findDeviceByDeviceNumber(id).map(DeviceDto::fromEntity).orElseThrow(() -> 
			new NoDataFoundException("Aucune Device Found With Id = " + id)
		);
	}
	
	@Override
	  public DeviceDto findById(Integer id) {
	    if (id == null) {
	      log.error("Device ID is null");
	      return null;
	    }
	    return deviceRepository.findById(id).map(DeviceDto::fromEntity).orElseThrow(() -> 
		new NoDataFoundException("Aucune Device Found With Id = " + id)
	        );
	  }

}
