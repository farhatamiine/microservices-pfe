package com.abdali.microhps.modechecker.producer;

import static com.abdali.microhps.modechecker.utils.Constants.TOPIC_PRECLEARED_SETTLEMENT_EVENTS;

import java.util.List;
import java.util.Random;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.abdali.microhps.modechecker.model.PreClearedTransaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PreClearedTransactionProducer {
	@Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;
    
    @Autowired
    ObjectMapper objectMapper;
    
    public ListenableFuture<SendResult<Integer, String>> sendClearedTransactionEvent(PreClearedTransaction preClearedTransaction) throws JsonProcessingException {
    	
    	Random rand = new Random(); 
        //generate random values from 0-24
        int int_random = rand.nextInt(25);
        
        Integer key = int_random; 
        String value = objectMapper.writeValueAsString(preClearedTransaction);
        
        ProducerRecord<Integer,String> producerRecord = buildProducerRecord(key, value, TOPIC_PRECLEARED_SETTLEMENT_EVENTS);

        ListenableFuture<SendResult<Integer,String>> listenableFuture =  kafkaTemplate.send(producerRecord);

        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, value, ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });
        return listenableFuture;
    }

    private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic) {
        List<Header> recordHeaders = List.of(new RecordHeader("event-source", "scanner".getBytes()));
        return new ProducerRecord<>(topic, null, key, value, recordHeaders);
    }
    
    private void handleFailure(Integer key, String value, Throwable ex) {
        log.error("Error Sending the pre cleared message and the exception is {}", ex.getMessage());
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in OnFailure: {}", throwable.getMessage());
        }
    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
        log.info("Pre Cleared Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value, result.getRecordMetadata().partition());
    }
}
