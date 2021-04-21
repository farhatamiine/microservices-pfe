package com.abdali.microhps.dropservice.controller;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abdali.microhps.dropservice.dto.DenominationDto;
import com.abdali.microhps.dropservice.dto.DropMessageDto;
import com.abdali.microhps.dropservice.model.Denomination;
import com.abdali.microhps.dropservice.service.DropMessageService;

@RestController
public class DropController {
	
	DropMessageService dropMessageService;
	
	@Autowired
	public DropController(
			DropMessageService dropMessageService
			) {
		this.dropMessageService = dropMessageService;
	}

	@PostMapping("/dropmessage/new")
	public DropMessageDto addMessage(@RequestBody String messageRequest) {
		
		String message = URLDecoder.decode(messageRequest, StandardCharsets.UTF_8);
		
		String[] messageArray = message.split(",");
		
		Instant transmitionDate = null; 

//		 -- Check for drop message and verify Merchant Number -- contain just numbers with 15 in length.
		Denomination denomination = new Denomination();	
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
		
		
		return dropMessageService.save(dropMessageDto);
	}
	
	@GetMapping("/dropmessage")
	public List<DropMessageDto> getMessages() {
		return dropMessageService.findAll();
	}
	
	@GetMapping("/dropmessage/{messageId}")
	public DropMessageDto getMessageById(@PathVariable("messageId") Long Id) {
		return dropMessageService.findById(Id);
	}
	
	@GetMapping("/dropmessage/merchant/{merchantNumber}")
	public List<DropMessageDto> getMessageByMerchantNumber(@PathVariable("merchantNumber") Long merchantNumber) {
		return dropMessageService.findByMerchantNumber(merchantNumber);
	}
	
	@GetMapping("/dropmessage/device/{deviceNumber}")
	public List<DropMessageDto> getMessageByDeviceNumber(@PathVariable("deviceNumber") String deviceNumber) {
		return dropMessageService.findByDeviceNumber(deviceNumber);
	}

	@GetMapping("/dropmessage/bag/{bagNumber}")
	public List<DropMessageDto> getMessageByBagNumber(@PathVariable("bagNumber") String bagNumber) {
		return dropMessageService.findByBagNumber(bagNumber);
	}
	
	@GetMapping("/dropmessage/verify/bag/{bagNumber}")
	public Boolean verifyTransactionForIntegrityBagNumber(@PathVariable("bagNumber") String bagNumber) {
		if(dropMessageService.findByBagNumber(bagNumber).isEmpty()) {
			return false;
		}
		return true;
	}
	
	@GetMapping("dropmessage/merchant/{merchantNumber}/bag/{bagNumber}/tranasction/{transactionId}/date/{datetime}")
	public Boolean verifyMessage(
			@PathVariable("merchantNumber") Long merchantNumber, 
			@PathVariable("bagNumber") String bagNumber, 
			@PathVariable("transactionId") Integer transactionId,
			@PathVariable("datetime") String transmitionDate) {

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
