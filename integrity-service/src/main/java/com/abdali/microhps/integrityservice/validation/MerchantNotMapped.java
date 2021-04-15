package com.abdali.microhps.integrityservice.validation;

import org.springframework.stereotype.Service;

//- Device not mapped: this validation fails in 2 different cases
//o Simple merchant: device do not belong to the merchant in the message.
//o Deposit merchant: deposit outlet and device not linked to the same sponsor outlet. ????????????????

@Service
public class MerchantNotMapped {

}
