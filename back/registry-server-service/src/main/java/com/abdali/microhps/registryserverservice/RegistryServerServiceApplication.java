package com.abdali.microhps.registryserverservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class RegistryServerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistryServerServiceApplication.class, args);
	}

}
