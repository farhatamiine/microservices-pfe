package com.abdali.microhps.devicemerchantservice.model;

public enum MerchantStatus {
	closed("C"), suspended("S"), deactivated("D"), activated("A"), normal("N");
	
	private String code;

    private MerchantStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
