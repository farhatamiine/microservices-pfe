package com.abdali.microhps.integrityservice.config.feign;

import org.springframework.context.annotation.Bean;

import feign.form.FormEncoder;

public class FeignSimpleEncoderConfig {
	@Bean
    public FormEncoder encoder() {
        return new FormEncoder();
    }
}
