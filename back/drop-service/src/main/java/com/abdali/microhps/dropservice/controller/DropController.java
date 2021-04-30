package com.abdali.microhps.dropservice.controller;

import static com.abdali.microhps.dropservice.utils.Constants.NOTES_INDICATOR;
import static com.abdali.microhps.dropservice.utils.Constants.COINS_INDICATOR;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abdali.microhps.dropservice.dto.DenominationDto;
import com.abdali.microhps.dropservice.dto.DropMessageDto;
import com.abdali.microhps.dropservice.model.Denomination;
import com.abdali.microhps.dropservice.service.DropMessageService;

@CrossOrigin("*")
@RestController
public class DropController {
	
	DropMessageService dropMessageService;
	
	@Autowired
	public DropController(
			DropMessageService dropMessageService
			) {
		this.dropMessageService = dropMessageService;
	}

	@PostMapping("/drop-message/new")
	public DropMessageDto addMessage(@RequestBody String messageRequest) {
		
		String message = URLDecoder.decode(messageRequest, StandardCharsets.UTF_8);
		
		String[] messageArray = message.split(",");

		for(int i =0; i < messageArray.length; i++) {
			messageArray[i] = messageArray[i].trim().replace("_+$", "");
		}
		
		Instant transmitionDate = null; 
		
		Denomination denomination = new Denomination();	
		
		if(messageArray[4].charAt(0) == COINS_INDICATOR) {			
			if(messageArray[14].contains("=")) {
				denomination.setDenomination1(Integer.parseInt(messageArray[14].split("=")[1]));
			}
			if(messageArray[15].contains("=")) {
				denomination.setDenomination2(Integer.parseInt(messageArray[15].split("=")[1]));
			}
			if(messageArray[16].contains("=")) {
				denomination.setDenomination3(Integer.parseInt(messageArray[16].split("=")[1]));
			}
			if(messageArray[17].contains("=")) {
				denomination.setDenomination4(Integer.parseInt(messageArray[17].split("=")[1]));
			}
			if(messageArray[18].contains("=")) {
				denomination.setDenomination5(Integer.parseInt(messageArray[18].split("=")[1]));
			}
			if(messageArray[19].contains("=")) {
				denomination.setDenomination6(Integer.parseInt(messageArray[19].split("=")[1]));
			}
		}
		
		if(messageArray[4].charAt(0) == NOTES_INDICATOR) {			
			if(messageArray[20].contains("=")) {
				denomination.setDenomination7(Integer.parseInt(messageArray[20].split("=")[1]));
			}
			if(messageArray[21].contains("=")) {
				denomination.setDenomination8(Integer.parseInt(messageArray[21].split("=")[1]));
			}
			if(messageArray[22].contains("=")) {
				denomination.setDenomination9(Integer.parseInt(messageArray[22].split("=")[1]));
			}
			if(messageArray[23].contains("=")) {
				denomination.setDenomination10(Integer.parseInt(messageArray[23].split("=")[1]));
			}
			if(messageArray[24].contains("=")) {
				denomination.setDenomination11(Integer.parseInt(messageArray[24].split("=")[1]));
			}
		}
		
		if(messageArray[25].contains("=")) {
			denomination.setDenomination12(Integer.parseInt(messageArray[25].split("=")[1]));
		}
		if(messageArray[26].contains("=")) {
			denomination.setDenomination13(Integer.parseInt(messageArray[26].split("=")[1]));
		}
		if(messageArray[27].contains("=")) {
			denomination.setDenomination14(Integer.parseInt(messageArray[27].split("=")[1]));
		}
		if(messageArray[28].contains("=")) {
			denomination.setDenomination15(Integer.parseInt(messageArray[28].split("=")[1]));
		}
				
		final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-ddHH:mm:ss:SSSSSS")
                .withZone(ZoneId.systemDefault());
		try {
			transmitionDate = Instant.from(formatter.parse(messageArray[8]));
		} catch (Exception e) {
//					throw new IntegrityException(MESSAGE_INVALID_CODE, "error date time");
		} 
				
		DropMessageDto dropMessageDto = DropMessageDto.builder()
				.indicator(messageArray[0].charAt(0)) 
				.merchantNumber(Long.parseLong(messageArray[1]))
				.deviceNumber(messageArray[2])
				.bagNumber(messageArray[3])
				.containerType(messageArray[4].charAt(0)) 
				.sequenceNumber(Integer.parseInt(messageArray[5]))
				.transactionId(Integer.parseInt(messageArray[6]))
				.depositReference(messageArray[7])
				.transmitionDate(transmitionDate)
				.canisterNumber(Integer.parseInt(messageArray[9]))
				.currency(messageArray[10])
				.totalAmount(new BigDecimal(messageArray[11]))
				.totalNotes(Integer.parseInt(messageArray[12]))
				.totalCoins(Integer.parseInt(messageArray[13]))
				.denomination(DenominationDto.fromEntity(denomination))
				.build();
		
		DropMessageDto savedDropMessage = dropMessageService.save(dropMessageDto);
		
		
		return savedDropMessage;
		
	}
	
	@GetMapping("/drop-transaction")
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
	
	@GetMapping("/drop-transaction/{messageId}")
	public DropMessageDto getMessageById(@PathVariable("messageId") Long Id) {
		return dropMessageService.findById(Id);
	}
	
	@GetMapping("/drop-transaction/merchant/{merchantNumber}")
	public List<DropMessageDto> getMessageByMerchantNumber(@PathVariable("merchantNumber") Long merchantNumber) {
		return dropMessageService.findByMerchantNumber(merchantNumber);
	}
	
	@GetMapping("/drop-transaction/device/{deviceNumber}")
	public List<DropMessageDto> getMessageByDeviceNumber(@PathVariable("deviceNumber") String deviceNumber) {
		return dropMessageService.findByDeviceNumber(deviceNumber);
	}

	@GetMapping("/drop-transaction/bag/{bagNumber}")
	public List<DropMessageDto> getMessageByBagNumber(@PathVariable("bagNumber") String bagNumber) {
		return dropMessageService.findByBagNumber(bagNumber);
	}
	
	/******************************** Integrity Checks ********************************************/
	
	/**********************************************
	 * @param bagNumber
	 * @return Boolean if there is dropTransaction correspond to bag Number
	 */
	@GetMapping("/drop-transaction/verify/bag/{bagNumber}")
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
	@GetMapping("drop-transaction/merchant/{merchantNumber}/bag/{bagNumber}/tranasction/{transactionId}/date/{datetime}")
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
}
