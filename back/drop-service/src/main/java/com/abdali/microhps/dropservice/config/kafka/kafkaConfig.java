package com.abdali.microhps.dropservice.config.kafka;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import com.abdali.microhps.dropservice.service.DropMessageService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableKafka
@Slf4j
public class kafkaConfig {

    DropMessageService dropMessageService;
    
    @Autowired
    public kafkaConfig(DropMessageService dropMessageService) {
    	this.dropMessageService = dropMessageService;
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory);
        factory.setConcurrency(3);
       // factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setErrorHandler(((thrownException, data) -> {
            log.info("Exception in consumerConfig is {} and the record is {}", thrownException.getMessage(), data);
            //persist
        }));
//        factory.setRetryTemplate(retryTemplate());
//        factory.setRecoveryCallback((context -> {
//            if(context.getLastThrowable().getCause() instanceof RecoverableDataAccessException){
//                //invoke recovery logic
//                log.info("Inside the recoverable logic");
//               /* Arrays.asList(context.attributeNames())
//                        .forEach(attributeName -> {
//                            log.info("Attribute name is : {} ", attributeName);
//                            log.info("Attribute Value is : {} ", context.getAttribute(attributeName));
//                        });*/
//
//               ConsumerRecord<Integer, String> consumerRecord = (ConsumerRecord<Integer, String>) context.getAttribute("record");
//                libraryEventsService.handleRecovery(consumerRecord);
//            }else{
//                log.info("Inside the non recoverable logic");
//                throw new RuntimeException(context.getLastThrowable().getMessage());
//            }
//
//
//            return null;
//        }));
        return factory;
    }

}
