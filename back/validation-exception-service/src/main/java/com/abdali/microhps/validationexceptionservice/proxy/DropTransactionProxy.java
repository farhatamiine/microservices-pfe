package com.abdali.microhps.validationexceptionservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="drop-service")
public interface DropTransactionProxy {

}
