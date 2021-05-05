package com.abdali.microhps.devicemerchantservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abdali.microhps.devicemerchantservice.model.MerchantAccount;

public interface MerchantAccountRepository extends JpaRepository<MerchantAccount, Integer> {

}
