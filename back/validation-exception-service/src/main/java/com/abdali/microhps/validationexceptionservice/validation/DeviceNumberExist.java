package com.abdali.microhps.validationexceptionservice.validation;

import org.springframework.stereotype.Service;

import com.abdali.microhps.validationexceptionservice.model.PowerCardNotification;
import com.abdali.microhps.validationexceptionservice.proxy.MerchantDeviceProxy;
import com.abdali.microhps.validationexceptionservice.repository.PowerCardNotificationRepository;

@Service
public class DeviceNumberExist {
	
	MerchantDeviceProxy merchantDeviceProxy;
	PowerCardNotificationRepository powerCardNotificationRepository;
	
	public DeviceNumberExist(
			MerchantDeviceProxy merchantDeviceProxy,
			PowerCardNotificationRepository powerCardNotificationRepository) {
		this.merchantDeviceProxy = merchantDeviceProxy;
		this.powerCardNotificationRepository = powerCardNotificationRepository;
	}
	
	public Boolean isDeviceNumberExist(String deviceNumber) { 
		if(!merchantDeviceProxy.isDeviceNumberExist(deviceNumber)) {
//			throw new Exception("device Number 222" + merchantDeviceProxy.isDeviceNumberExist(deviceNumber));
//			// DONE : ADD Notification in PowerCard. 
			PowerCardNotification powerCardNotification = new PowerCardNotification();
			powerCardNotification.setNotificationTitle("Merchant Number Didnt Found");
			powerCardNotification.setNotificationDescription("There is no Device with Number : " + deviceNumber + " in our device Outlet");
			powerCardNotification.setDeviceNumber(deviceNumber);
			powerCardNotificationRepository.save(powerCardNotification);
			return false;
		}
		return true;
	}
	
	
}
