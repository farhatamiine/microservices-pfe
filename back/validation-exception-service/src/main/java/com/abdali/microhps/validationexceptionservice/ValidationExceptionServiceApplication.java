package com.abdali.microhps.validationexceptionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableEurekaClient
@EnableJpaAuditing
@EnableFeignClients
public class ValidationExceptionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValidationExceptionServiceApplication.class, args);
	}

}
