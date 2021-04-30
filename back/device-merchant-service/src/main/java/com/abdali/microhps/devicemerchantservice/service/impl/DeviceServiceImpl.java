package com.abdali.microhps.devicemerchantservice.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.abdali.microhps.devicemerchantservice.dto.DeviceDto;
import com.abdali.microhps.devicemerchantservice.exceptions.types.InvalidEntityException;
import com.abdali.microhps.devicemerchantservice.exceptions.types.NoDataFoundException;
import com.abdali.microhps.devicemerchantservice.model.Device;
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
	public Map<String, Object> findAll(String deviceNumber, int page, int size) {

	      List<DeviceDto> devices = new ArrayList<DeviceDto>();
	      
	      Pageable pagingSort = PageRequest.of(page, size);

	      Page<Device> pageDevices = null;
	      
	      if (deviceNumber == null)
	        pageDevices = deviceRepository.findAll(pagingSort);

	      List<DeviceDto> devicesDto = pageDevices.getContent().stream()
					.map(DeviceDto::fromEntity)
					.collect(Collectors.toList());

	      Map<String, Object> response = new HashMap<>();
	      
	      response.put("devices", devicesDto);
	      response.put("currentPage", pageDevices.getNumber());
	      response.put("totalItems", pageDevices.getTotalElements());
	      response.put("totalPages", pageDevices.getTotalPages());
	      
	      return response;
	}
	
	@Override
	public DeviceDto save(DeviceDto deviceDto) {
		List<String> errors = DeviceValidation.validate(deviceDto);
		
	    if (!errors.isEmpty()) {
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
	public DeviceDto findByDeviceNumber(String deviceNumber) {
		if (deviceNumber == null) {
	      return null;
	    }
		return deviceRepository.findDeviceByDeviceNumber(deviceNumber).map(DeviceDto::fromEntity).orElseThrow(() -> 
			new NoDataFoundException("Aucune Device Found With Id = " + deviceNumber)
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
	

    /***
     * Sorting Devices.
     * @param direction
     * @return
     */
    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
          return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
          return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
      }

}
