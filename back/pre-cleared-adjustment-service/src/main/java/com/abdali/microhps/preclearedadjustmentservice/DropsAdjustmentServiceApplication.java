package com.abdali.microhps.preclearedadjustmentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableEurekaClient
@EnableJpaAuditing
@EnableFeignClients
public class DropsAdjustmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DropsAdjustmentServiceApplication.class, args);
	}

}
