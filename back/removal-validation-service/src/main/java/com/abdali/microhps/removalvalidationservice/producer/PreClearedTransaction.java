package com.abdali.microhps.removalvalidationservice.producer;

import java.util.List;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
 
import com.abdali.microhps.removalvalidationservice.model.AdjustementEvent;
import com.abdali.microhps.removalvalidationservice.model.CoreTransactionModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PreClearedTransaction {
	@Autowired
    KafkaTemplate<Long,String> kafkaTemplate;
    
    @Autowired
    ObjectMapper objectMapper;
    
    public ListenableFuture<SendResult<Long,String>> sendTransactionEvent(CoreTransactionModel coreTransactionModel, String topic) throws JsonProcessingException {

        Long key = coreTransactionModel.getId();
        String value = objectMapper.writeValueAsString(coreTransactionModel);
        
        ProducerRecord<Long,String> producerRecord = buildProducerRecord(key, value, topic);

        ListenableFuture<SendResult<Long,String>> listenableFuture =  kafkaTemplate.send(producerRecord);

        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Long, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, value, ex);
            }

            @Override
            public void onSuccess(SendResult<Long, String> result) {
                handleSuccess(key, value, result);
            }
        });
        return listenableFuture;
    }

    private ProducerRecord<Long, String> buildProducerRecord(Long key, String value, String topic) {
        List<Header> recordHeaders = List.of(new RecordHeader("event-source", "scanner".getBytes()));
        return new ProducerRecord<>(topic, null, key, value, recordHeaders);
    }
    
    private void handleFailure(Long key, String value, Throwable ex) {
        log.error("Error Sending the pre cleared message and the exception is {}", ex.getMessage());
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in OnFailure: {}", throwable.getMessage());
        }
    }

    private void handleSuccess(Long key, String value, SendResult<Long, String> result) {
        log.info("Pre Cleared Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value, result.getRecordMetadata().partition());
    }
}
