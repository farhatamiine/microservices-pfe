package com.abdali.microhps.devicemerchantservice.dto;

import java.util.List;

import com.abdali.microhps.devicemerchantservice.model.Device;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceDto {

	private Integer id;
	private Integer deviceNumber;

	@JsonIgnore
	private List<MerchantDto> merchants;
	
	public static DeviceDto fromEntity(Device device) {
		if(device == null) {
			return null;
		}
		
		return DeviceDto.builder()
				.id(device.getId())
				.deviceNumber(device.getDeviceNumber())
				.build();
	}
	
	public static Device toEntity(DeviceDto deviceDto) {
		if(deviceDto == null) {
			return null;
		}
		
		Device device = new Device();
		device.setId(deviceDto.getId());
		device.setDeviceNumber(deviceDto.getDeviceNumber());
		return device;
	}
}
