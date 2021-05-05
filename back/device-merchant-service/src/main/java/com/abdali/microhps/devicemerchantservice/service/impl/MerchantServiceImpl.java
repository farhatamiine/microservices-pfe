package com.abdali.microhps.devicemerchantservice.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abdali.microhps.devicemerchantservice.dto.MerchantDto; 
import com.abdali.microhps.devicemerchantservice.exceptions.types.InvalidEntityException;
import com.abdali.microhps.devicemerchantservice.exceptions.types.NoDataFoundException;
import com.abdali.microhps.devicemerchantservice.model.Merchant; 
import com.abdali.microhps.devicemerchantservice.model.MerchantStatus; 
import com.abdali.microhps.devicemerchantservice.repository.DeviceRepository;
import com.abdali.microhps.devicemerchantservice.repository.MerchantRepository; 
import com.abdali.microhps.devicemerchantservice.service.MerchantService;
import com.abdali.microhps.devicemerchantservice.validation.MerchantValidation;

import lombok.extern.slf4j.Slf4j;
 
@Service
@Slf4j
public class MerchantServiceImpl implements MerchantService {

	private MerchantRepository merchantRepository;
	private DeviceRepository deviceRepository;
	
	@Autowired
	public MerchantServiceImpl(
			MerchantRepository merchantRepository,
			DeviceRepository deviceRepository
			) {
		this.merchantRepository = merchantRepository;
		this.deviceRepository = deviceRepository;
	}

	// - save 
	// - find by id
	// - list 
	
	@Override
	public MerchantDto save(MerchantDto merchantDto) {
		List<String> errors = MerchantValidation.validate(merchantDto);
		if (!errors.isEmpty()) {
			throw new InvalidEntityException("errors code :", errors);
		}
		
//		Optional<Device> device = deviceRepository.findById(merchantDto.getDevices().getId());
//	    if (device.isEmpty()) {
//	      throw new NoDataFoundException("There is no Device with ID " + merchantDto.getDevices().getId() + " found in the Database");
//	    } 
	    
		// TODO Auto-generated method stub 
		
//		throw new NoDataFoundException("errors code :" + merchantDto);
		return MerchantDto.fromEntity(merchantRepository.save(MerchantDto.toEntity(merchantDto)));
	}
	
	@Override
	public MerchantDto findById(Integer merchantId) {
		if(merchantId == null) {
			return null;
		}
		return merchantRepository.findById(merchantId).map(MerchantDto::fromEntity).orElseThrow(() ->
			new NoDataFoundException("Aucune Merchant Found With Id = " + merchantId)
		);
	}
	
	@Override
	public MerchantDto findByMerchantNumber(Long merchantNumber) {
		if(merchantNumber == null) {
			return null;
		}
		return merchantRepository.findByMerchantNumber(merchantNumber).map(MerchantDto::fromEntity).orElse(null);
	}
	
	public Map<String, Object> findAll(Long merchantNumber, int page, int size) {
		
		List<MerchantDto> merchants = new ArrayList<MerchantDto>();
	      
	      Pageable pagingSort = PageRequest.of(page, size);

	      Page<Merchant> pageMerchants = null;
	      
	      if (merchantNumber == null)
	        pageMerchants = merchantRepository.findAll(pagingSort);

	      List<MerchantDto> merchantsDto = pageMerchants.getContent().stream()
					.map(MerchantDto::fromEntity)
					.collect(Collectors.toList());

	      Map<String, Object> response = new HashMap<>();
	      
	      response.put("merchants", merchantsDto);
	      response.put("currentPage", pageMerchants.getNumber());
	      response.put("totalItems", pageMerchants.getTotalElements());
	      response.put("totalPages", pageMerchants.getTotalPages());
	      
	      return response;
	}

	@Override
	public Boolean merchantCheckStatus(Long merchantNumber, MerchantStatus status) {
		
		// TODO : EXception to check if merchant Number exist if not Notify PowerCard.
//		if(findByMerchantNumber(merchantNumber) == null) {
//			PowerCardNotification powerCardNotification = new PowerCardNotification();
//			powerCardNotification.setNotificationTitle("Merchant Number Didnt Found");
//			powerCardNotification.setNotificationDescription("vendor with merchant Number : " + merchantNumber + "didnt found in our database");
//			powerCardNotification.setMerchantNumber(merchantNumber);
//			powerCardNotificationRepository.save(powerCardNotification);
//			return false;
//		}
		if (merchantNumber == null || merchantRepository.merchantStatus(merchantNumber, status).equals(0)) {
//			log.warn("ID merchant is NULL");
//			return Integer.valueOf(1);
			return false;
		}
		return true;
	}

}
