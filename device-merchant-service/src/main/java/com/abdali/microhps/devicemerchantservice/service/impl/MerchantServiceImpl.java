package com.abdali.microhps.devicemerchantservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdali.microhps.devicemerchantservice.dto.MerchantDto;
import com.abdali.microhps.devicemerchantservice.exceptions.types.InvalidEntityException;
import com.abdali.microhps.devicemerchantservice.exceptions.types.NoDataFoundException;
import com.abdali.microhps.devicemerchantservice.model.Device;
import com.abdali.microhps.devicemerchantservice.model.Merchant;
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
			DeviceRepository deviceRepository) {
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
		
		Optional<Device> device = deviceRepository.findById(merchantDto.getDevice().getId());
	    if (device.isEmpty()) {
	      throw new NoDataFoundException("There is no Device with ID " + merchantDto.getDevice().getId() + " found in the Database");
	    } 
	    
		// TODO Auto-generated method stub
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
		return merchantRepository.findByMerchantNumber(merchantNumber).map(MerchantDto::fromEntity).orElseThrow(() ->
			new NoDataFoundException("Aucune Merchant Found With Id = " + merchantNumber)
		);
	}
	
	@Override
	public List<MerchantDto> findAllMerchantByIdDevice(Integer idDevice) {
		return merchantRepository.findAllByDeviceId(idDevice).stream().map(MerchantDto::fromEntity).collect(Collectors.toList());
	}
	
	public List<MerchantDto> findAll() {
		return merchantRepository.findAll().stream().map(MerchantDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public Boolean merchantCheckStatus(Long merchantNumber, List<String> status) {
		if (merchantNumber == null) {
//			log.warn("ID merchant is NULL");
//			return Integer.valueOf(1);
		}
		if(merchantRepository.merchantStatus(merchantNumber, status) == 0) {
			return false;
		};
		return true;
	}

}
