package com.abdali.microhps.integrityservice.validation;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.integrityservice.proxy.MerchantDeviceProxy;
 

//- Merchant not found: this validation fails when the merchant number meet any of the following conditions
//o Not found in PowerCARD outlet table.
//o Outlet status is closed.
//o Outlet status is suspended.
//o Outlet status is deactivated.

@Service
public class MerchantNotFound {
	
	private MerchantDeviceProxy merchantDeviceProxy;
	
	@Autowired
	public MerchantNotFound(MerchantDeviceProxy merchantDeviceProxy) {
		this.merchantDeviceProxy = merchantDeviceProxy;
	}

	public Boolean checkMerchantNotFound(String Merchant) {
		
		return merchantDeviceProxy.checkMerchantState(Long.valueOf(Merchant));
	}

}
