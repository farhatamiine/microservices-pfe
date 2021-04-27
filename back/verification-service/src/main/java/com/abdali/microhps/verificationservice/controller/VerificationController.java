package com.abdali.microhps.verificationservice.controller;

import static com.abdali.microhps.verificationservice.utils.Constants.COINS_INDICATOR;
import static com.abdali.microhps.verificationservice.utils.Constants.NOTES_INDICATOR;

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

import com.abdali.microhps.verificationservice.dto.DenominationDto;
import com.abdali.microhps.verificationservice.dto.VerificationMessageDto;
import com.abdali.microhps.verificationservice.model.Denomination;
import com.abdali.microhps.verificationservice.service.VerificationMessageService;

@RestController
public class VerificationController {
	
	VerificationMessageService verificationMessageService;
	
	@Autowired
	public VerificationController(
			VerificationMessageService verificationMessageService
			) {
		this.verificationMessageService = verificationMessageService;
	}

	@PostMapping("/verification-transaction/new")
	public VerificationMessageDto addMessage(@RequestBody String messageRequest) {
		
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
			transmitionDate = Instant.from(formatter.parse(messageArray[5]));
		} catch (Exception e) {
//					throw new IntegrityException(MESSAGE_INVALID_CODE, "error date time");
		} 
				
		VerificationMessageDto verificationMessageDto = VerificationMessageDto.builder()
				.indicator(messageArray[0].charAt(0)) 
				.deviceNumber(messageArray[1])
				.bagNumber(messageArray[2])
				.containerType(messageArray[3].charAt(0)) 
				.transactionId(Integer.parseInt(messageArray[4]))
				.transmitionDate(transmitionDate)
				.canisterNumber(Integer.parseInt(messageArray[6]))
				.currency(messageArray[7])
				.cashCenterType(messageArray[8])
				.cashCenterCode(messageArray[9])
				.totalAmount(new BigDecimal(messageArray[10]))
				.totalNotes(Integer.parseInt(messageArray[11]))
				.totalCoins(Integer.parseInt(messageArray[12]))
				.denomination(DenominationDto.fromEntity(denomination))
				.build();
				
		return verificationMessageService.save(verificationMessageDto);
	}
	
	@GetMapping("/verification-transaction")
	public List<VerificationMessageDto> getMessages() {
		return verificationMessageService.findAll();
	}
	
	@GetMapping("/verification-transaction/{messageId}")
	public VerificationMessageDto getMessageById(@PathVariable("messageId") Long Id) {
		return verificationMessageService.findById(Id);
	}

	@GetMapping("/verification-transaction/device/{deviceNumber}")
	public List<VerificationMessageDto> getMessageByDeviceNumber(@PathVariable("deviceNumber") String deviceNumber) {
		return verificationMessageService.findByDeviceNumber(deviceNumber);
	}

	@GetMapping("/verification-transaction/bag/{bagNumber}")
	public List<VerificationMessageDto> getMessageByBagNumber(@PathVariable("bagNumber") String bagNumber) {
		return verificationMessageService.findByBagNumber(bagNumber);
	}
	
	@GetMapping("/verification-transaction/verify/bag/{bagNumber}")
	public Boolean verifyTransactionForIntegrityBagNumber(@PathVariable("bagNumber") String bagNumber) {
		if(verificationMessageService.findByBagNumber(bagNumber).isEmpty()) {
			return false;
		}
		return true;
	}
}
