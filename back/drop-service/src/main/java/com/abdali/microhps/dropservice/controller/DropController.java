package com.abdali.microhps.dropservice.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abdali.microhps.dropservice.dto.DropCoreTransactionDto;
import com.abdali.microhps.dropservice.service.DropMessageService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@RestController
@Slf4j
@RequestMapping("/drop-transaction")
public class DropController {
	
	DropMessageService dropMessageService;
	
	@Autowired
	public DropController(
			DropMessageService dropMessageService
			) {
		this.dropMessageService = dropMessageService;
	}
	
	/**
	 * All drop Transactions with pagination.
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getMessages(
		      @RequestParam(defaultValue = "0") int page,
		      @RequestParam(defaultValue = "10") int size
		     ) {
		try {
	      Map<String, Object> result = dropMessageService.findAll(page, size);

	      if(result.get("transactions").toString() == "[]" ) {
	    	  return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);  
	      }	      
	      return new ResponseEntity<>(result, HttpStatus.OK);
	      
	    } catch (Exception e) {
	    	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping("/{messageId}")
	public DropCoreTransactionDto getMessageById(@PathVariable("messageId") Long Id) {
		return dropMessageService.findById(Id);
	}
	
	@GetMapping("/merchant/{merchantNumber}")
	public List<DropCoreTransactionDto> getMessageByMerchantNumber(@PathVariable("merchantNumber") Long merchantNumber) {
		return dropMessageService.findByMerchantNumber(merchantNumber);
	}
	
	@GetMapping("/device/{deviceNumber}")
	public List<DropCoreTransactionDto> getMessageByDeviceNumber(@PathVariable("deviceNumber") String deviceNumber) {
		return dropMessageService.findByDeviceNumber(deviceNumber);
	}

	@GetMapping("/bag/{bagNumber}")
	public List<DropCoreTransactionDto> getMessageByBagNumber(@PathVariable("bagNumber") String bagNumber) {
		return dropMessageService.findByBagNumber(bagNumber);
	}
	
	/******************************** Integrity Checks ********************************************/
	
	/**********************************************
	 * @param bagNumber
	 * @return Boolean if there is dropTransaction correspond to bag Number
	 */
	@GetMapping("/verify/bag/{bagNumber}")
	public Boolean isBagNumberHasDrops(@PathVariable("bagNumber") String bagNumber) {
		if(dropMessageService.findByBagNumber(bagNumber).isEmpty()) {
			return false;
		}
		return true;
	}
	
	/***********************************************
	 * 
	 * @param merchantNumber
	 * @param bagNumber
	 * @param transactionId
	 * @param transmitionDate
	 * @return Boolean if dropTransaction founded.
	 * @throws Exception
	 */
	@GetMapping("/merchant/{merchantNumber}/bag/{bagNumber}/tranasction/{transactionId}/date/{datetime}")
	public Boolean isMessageExist(
			@PathVariable("merchantNumber") Long merchantNumber, 
			@PathVariable("bagNumber") String bagNumber, 
			@PathVariable("transactionId") Integer transactionId,
			@PathVariable("datetime") String transmitionDate) throws Exception {

		transmitionDate = URLDecoder.decode(transmitionDate, StandardCharsets.UTF_8);
		
		final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
                .withZone(ZoneId.systemDefault());
		Instant transmitionInstant = Instant.from(formatter.parse(transmitionDate));
		
		if(dropMessageService.findByMerchantNumberAndBagNumberAndTransactionIdAndTransmitionDate(merchantNumber, bagNumber, transactionId, transmitionInstant).isEmpty()) {
			return false;
		}
		return true;
	}
	
	/*
	 * Duplicated Transaction.
	 * @param TransactionId
	 * @return true if its Duplicated, false if not.
	 */
	@PostMapping("/transactionId/{transactionId}")
	public Boolean isTransactionIdDuplicated(@PathVariable Integer transactionId) {
		return dropMessageService.findByTransactionId(transactionId);
	}
}
