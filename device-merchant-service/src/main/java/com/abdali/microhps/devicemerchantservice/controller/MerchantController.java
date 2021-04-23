package com.abdali.microhps.devicemerchantservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abdali.microhps.devicemerchantservice.dto.DeviceDto;
import com.abdali.microhps.devicemerchantservice.dto.MerchantDto;
import com.abdali.microhps.devicemerchantservice.model.MerchantStatus;
import com.abdali.microhps.devicemerchantservice.service.MerchantService;
 
@RestController
public class MerchantController {
	
	private MerchantService merchantService;
	
	@Autowired
	public MerchantController(MerchantService merchantService) {
		this.merchantService = merchantService;
	}
	
	@PostMapping(value = "/merchant-device/merchants", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public MerchantDto addMerchant(@RequestBody MerchantDto merchantDto ) {
		return merchantService.save(merchantDto);
	}
	
	@GetMapping(value = "/merchant-device/merchants/merchant-id/{merchantId}")
	public MerchantDto getMerchantById(@PathVariable Integer merchantId) {
		return merchantService.findById(merchantId);
	}
	
	@GetMapping(value = "/merchant-device/merchants/merchant-number/{merchantNumber}")
	public MerchantDto getMerchantByNumber(@PathVariable Long merchantNumber) {
		return merchantService.findByMerchantNumber(merchantNumber);
	}
	
	@GetMapping(value= "/merchant-device/merchants")
	public List<MerchantDto> allMerchant() {
		return merchantService.findAll();
	}
	
	/*****************************************
	 * 
	 * @param merchantNumber
	 * @param deviceNumber
	 * @return Boolean : check if merchant has relation with device
	 */
	@GetMapping(value = "/merchant-device/merchant-number/{merchantNumber}/device-number/{deviceNumber}")
	public Boolean isDeviceRelatedToMerchant(@PathVariable Long merchantNumber, @PathVariable String deviceNumber) {
		
		MerchantDto merchant = merchantService.findByMerchantNumber(merchantNumber);
		if(merchant.getDevices().isEmpty()) {
			return false;
		} else {		
			return merchant.getDevices().stream().map(DeviceDto::getDeviceNumber).filter(deviceNumber::equals).findFirst().isPresent();
		}
	}
	
	@GetMapping("/merchant-device/status/{merchantNumber}")
	public Boolean checkMerchantState(@PathVariable("merchantNumber") Long merchantNumber) throws Exception {
		return merchantService.merchantCheckStatus(merchantNumber, MerchantStatus.normal);
	}
	
	
}
