package com.abdali.microhps.removalservice.controller;

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

import com.abdali.microhps.removalservice.dto.DenominationDto;
import com.abdali.microhps.removalservice.dto.RemovalMessageDto;
import com.abdali.microhps.removalservice.model.Denomination;
import com.abdali.microhps.removalservice.model.MessageRequest;
import com.abdali.microhps.removalservice.service.RemovalMessageService;

@RestController
public class RemovalController {
	
	RemovalMessageService removalMessageService;
	
	@Autowired
	public RemovalController(
			RemovalMessageService removalMessageService
			) {
		this.removalMessageService = removalMessageService;
	}

	@PostMapping("/removalmessage/new")
	public RemovalMessageDto addMessage(@RequestBody String messageRequest) {
		
		String message = URLDecoder.decode(messageRequest, StandardCharsets.UTF_8);
		
		String[] messageArray = message.split(",");
		
		Instant transmitionDate = null; 
				
//		 -- Check for removal message and verify Merchant Number -- contain just numbers with 15 in length.
		Denomination denomination = new Denomination();	
		if(messageArray[13].contains("=")) {
			denomination.setDenomination1(Integer.parseInt(messageArray[13].split("=")[1]));
		}
		if(messageArray[14].contains("=")) {
			denomination.setDenomination2(Integer.parseInt(messageArray[14].split("=")[1]));
		}
		if(messageArray[15].contains("=")) {
			denomination.setDenomination3(Integer.parseInt(messageArray[15].split("=")[1]));
		}
		if(messageArray[16].contains("=")) {
			denomination.setDenomination4(Integer.parseInt(messageArray[16].split("=")[1]));
		}
		if(messageArray[17].contains("=")) {
			denomination.setDenomination5(Integer.parseInt(messageArray[17].split("=")[1]));
		}
		if(messageArray[18].contains("=")) {
			denomination.setDenomination6(Integer.parseInt(messageArray[18].split("=")[1]));
		}
		if(messageArray[19].contains("=")) {
			denomination.setDenomination7(Integer.parseInt(messageArray[19].split("=")[1]));
		}
		if(messageArray[20].contains("=")) {
			denomination.setDenomination8(Integer.parseInt(messageArray[20].split("=")[1]));
		}
		if(messageArray[21].contains("=")) {
			denomination.setDenomination9(Integer.parseInt(messageArray[21].split("=")[1]));
		}
		if(messageArray[22].contains("=")) {
			denomination.setDenomination10(Integer.parseInt(messageArray[22].split("=")[1]));
		}
		if(messageArray[23].contains("=")) {
			denomination.setDenomination11(Integer.parseInt(messageArray[23].split("=")[1]));
		}
		if(messageArray[24].contains("=")) {
			denomination.setDenomination12(Integer.parseInt(messageArray[24].split("=")[1]));
		}
		if(messageArray[25].contains("=")) {
			denomination.setDenomination13(Integer.parseInt(messageArray[25].split("=")[1]));
		}
		if(messageArray[26].contains("=")) {
			denomination.setDenomination14(Integer.parseInt(messageArray[26].split("=")[1]));
		}
		if(messageArray[27].contains("=")) {
			denomination.setDenomination15(Integer.parseInt(messageArray[27].split("=")[1]));
		}
				
		final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-ddHH:mm:ss:SSSSSS")
                .withZone(ZoneId.systemDefault());
		try {
			transmitionDate = Instant.from(formatter.parse(messageArray[7]));
		} catch (Exception e) {
//			throw new IntegrityException(MESSAGE_INVALID_CODE, "error date time");
		} 
				
		RemovalMessageDto removalMessageDto = RemovalMessageDto.builder()
				.indicator(messageArray[0].charAt(0)) 
				.deviceNumber(messageArray[1])
				.bagNumber(messageArray[2])
				.containerType(messageArray[3].charAt(0)) 
				.sequenceNumber(Integer.parseInt(messageArray[4]))
				.transactionId(Integer.parseInt(messageArray[5]))
				.depositReference(messageArray[6])
				.transmitionDate(transmitionDate)
				.canisterNumber(Integer.parseInt(messageArray[8]))
				.currency(messageArray[9])
				.totalAmount(new BigDecimal(messageArray[10]))
				.totalNotes(Integer.parseInt(messageArray[11]))
				.totalCoins(Integer.parseInt(messageArray[12]))
				.denomination(DenominationDto.fromEntity(denomination))
				.build();
				
		return removalMessageService.save(removalMessageDto);
	}
	
	@GetMapping("/removalmessage")
	public List<RemovalMessageDto> getMessages() {
		return removalMessageService.findAll();
	}
	
	@GetMapping("/removalmessage/{messageId}")
	public RemovalMessageDto getMessageById(@PathVariable("messageId") Long Id) {
		return removalMessageService.findById(Id);
	}

	@GetMapping("/removalmessage/device/{deviceNumber}")
	public List<RemovalMessageDto> getMessageByDeviceNumber(@PathVariable("deviceNumber") String deviceNumber) {
		return removalMessageService.findByDeviceNumber(deviceNumber);
	}

	@GetMapping("/removalmessage/bag/{bagNumber}")
	public List<RemovalMessageDto> getMessageByBagNumber(@PathVariable("bagNumber") String bagNumber) {
		return removalMessageService.findByBagNumber(bagNumber);
	}
	
	@GetMapping("/removalmessage/verify/bag/{bagNumber}")
	public Boolean verifyTransactionForIntegrityBagNumber(@PathVariable("bagNumber") String bagNumber) {
		if(removalMessageService.findByBagNumber(bagNumber).isEmpty()) {
			return false;
		}
		return true;
	}
}
