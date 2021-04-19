package com.abdali.microhps.devicemerchantservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abdali.microhps.devicemerchantservice.model.Merchant;
import com.abdali.microhps.devicemerchantservice.model.MerchantStatus;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {
	
		// Merchant Not Found
//		@Query("select count(m.id) FROM Merchant m where m.merchantNumber = :MerchantNumber AND m.status NOT IN :Status")
//		Integer merchantStatus(@Param("MerchantNumber") Long merchantNumber, @Param("Status") List<String> status);
	
		@Query("select count(m.id) FROM Merchant m where m.merchantNumber = :MerchantNumber AND m.status = :Status")
		Integer merchantStatus(@Param("MerchantNumber") Long merchantNumber, @Param("Status") MerchantStatus status);
		  
		Optional<Merchant> findByMerchantNumber(Long merchantNumber);
		
		List<Merchant> findAllByDeviceId(Integer idDevice);
		
		
}
