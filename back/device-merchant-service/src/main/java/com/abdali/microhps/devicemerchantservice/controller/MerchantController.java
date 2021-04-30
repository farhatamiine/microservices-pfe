package com.abdali.microhps.devicemerchantservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abdali.microhps.devicemerchantservice.dto.DeviceDto;
import com.abdali.microhps.devicemerchantservice.dto.MerchantDto;
import com.abdali.microhps.devicemerchantservice.model.MerchantStatus;
import com.abdali.microhps.devicemerchantservice.service.MerchantService;
 
@CrossOrigin("*")
@RestController
@RequestMapping("/merchant-device")
public class MerchantController {
	
	private MerchantService merchantService;
	
	@Autowired
	public MerchantController(MerchantService merchantService) {
		this.merchantService = merchantService;
	}
	
	@PostMapping(value = "/merchants", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public MerchantDto addMerchant(@RequestBody MerchantDto merchantDto ) {
		return merchantService.save(merchantDto);
	}
	
	@GetMapping(value = "/merchants/merchant-id/{merchantId}")
	public MerchantDto getMerchantById(@PathVariable Integer merchantId) {
		return merchantService.findById(merchantId);
	}
	
	@GetMapping(value = "/merchants/merchant-number/{merchantNumber}")
	public MerchantDto getMerchantByNumber(@PathVariable Long merchantNumber) {
		return merchantService.findByMerchantNumber(merchantNumber);
	}
	
	@GetMapping(value= "/merchants")
	public ResponseEntity<Map<String, Object>> allMerchant(
		@RequestParam(required = false) Long merchantNumber,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
			) {
		try {
	      Map<String, Object> result = merchantService.findAll(merchantNumber, page, size);

	      if(result.get("merchants").toString() == "[]" ) {
	    	  return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);  
	      }	      
	      return new ResponseEntity<>(result, HttpStatus.OK);
	      
	    } catch (Exception e) {
	    	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	/*****************************************
	 * 
	 * @param merchantNumber
	 * @param deviceNumber
	 * @return Boolean : check if merchant has relation with device
	 */
	@GetMapping(value = "/merchant-number/{merchantNumber}/device-number/{deviceNumber}")
	public Boolean isDeviceRelatedToMerchant(@PathVariable Long merchantNumber, @PathVariable String deviceNumber) {
		
		MerchantDto merchant = merchantService.findByMerchantNumber(merchantNumber);
		if(merchant.getDevices().isEmpty()) {
			return false;
		} else {		
			return merchant.getDevices().stream().map(DeviceDto::getDeviceNumber).filter(deviceNumber::equals).findFirst().isPresent();
		}
	}
	
	@GetMapping("/status/{merchantNumber}")
	public Boolean checkMerchantState(@PathVariable("merchantNumber") Long merchantNumber) throws Exception {
		return merchantService.merchantCheckStatus(merchantNumber, MerchantStatus.normal);
	}
	
	
}
