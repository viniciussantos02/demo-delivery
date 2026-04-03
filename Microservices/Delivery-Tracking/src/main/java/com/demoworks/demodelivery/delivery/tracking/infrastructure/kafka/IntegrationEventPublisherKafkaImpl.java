package com.demoworks.demodelivery.delivery.tracking.infrastructure.kafka;

import com.demoworks.demodelivery.delivery.tracking.infrastructure.event.IntegrationEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class IntegrationEventPublisherKafkaImpl implements IntegrationEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(Object event, String key, String topic) {
        SendResult<String, Object> result = kafkaTemplate.send(topic, key, event).join(); //.join() aqui esta sendo usado apenas para debug, mas o ideal é que nao use ele, para manter a natureza assíncrona do envio da mensagem, ou seja, o método publish nao deve esperar o resultado do envio da mensagem para retornar
        RecordMetadata recordMetadata = result.getRecordMetadata();
        log.info("Message Publish: \n\t Topic: {} \n\t Offset: {}", recordMetadata.topic(), recordMetadata.offset());
    }
}
