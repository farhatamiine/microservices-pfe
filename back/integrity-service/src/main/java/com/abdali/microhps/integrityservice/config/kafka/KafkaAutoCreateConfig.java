package com.abdali.microhps.integrityservice.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaAutoCreateConfig {
	@Bean
    public NewTopic transactionEvents(){
        return TopicBuilder.name("transaction-events")
                .partitions(3)
                .replicas(3)
                .build();
    }
}