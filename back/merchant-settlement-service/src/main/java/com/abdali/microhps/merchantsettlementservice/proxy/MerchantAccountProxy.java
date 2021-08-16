package com.abdali.microhps.merchantsettlementservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="device-merchant-service")
public interface MerchantAccountProxy {

}
