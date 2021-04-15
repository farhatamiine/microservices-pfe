package com.abdali.microhps.integrityservice.validation;
 
import org.springframework.stereotype.Service;
 

//- Merchant not found: this validation fails when the merchant number meet any of the following conditions
//o Not found in PowerCARD outlet table.
//o Outlet status is closed.
//o Outlet status is suspended.
//o Outlet status is deactivated.

@Service
public class MerchantNotFound {
	
//	@Autowired
//	private MerchantService merchantService;
//	
//	public Boolean checkMerchantNotFound(String Merchant) {
//		Boolean messageReturn = false; 
//		
//		int value = merchantService.merchantCheckStatus(Long.valueOf(Merchant), POWERCARD_OUTLET_STATUS);
////		return "value = " + value + POWERCARD_OUTLET_STATUS;
//		if(value == 0) {
//			throw new NoDataFoundException("Merchant Not Found " + Merchant);
//		} else {
//			messageReturn = true;
//		}
//		
//		return messageReturn;
//	}

}
