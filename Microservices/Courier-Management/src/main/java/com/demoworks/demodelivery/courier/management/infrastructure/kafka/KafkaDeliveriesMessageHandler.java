package com.demoworks.demodelivery.courier.management.infrastructure.kafka;

import com.demoworks.demodelivery.courier.management.domain.service.CourierDeliveryService;
import com.demoworks.demodelivery.courier.management.infrastructure.event.DeliveryFulfilledIntegrationEvent;
import com.demoworks.demodelivery.courier.management.infrastructure.event.DeliveryPlacedIntegrationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(topics = { //Define o/os topicos que este listener irá consumir, nesse caso apenas 1 foi definido
        "deliveries.v1.events"
}, groupId = "courier-management")
@RequiredArgsConstructor
public class KafkaDeliveriesMessageHandler {

    private final CourierDeliveryService courierDeliveryService;

    @KafkaHandler(isDefault = true) //Define que esse método é o handler padrão, ou seja, caso a mensagem recebida não seja do tipo esperado pelos outros handlers, ela será tratada por esse método default
    public void defaultHandler(@Payload Object object) {
        log.info("Default Handler: {}", object);
    }

    @KafkaHandler
    public void handle(@Payload DeliveryPlacedIntegrationEvent event) {
        log.info("Received event: {}", event);
        courierDeliveryService.assign(event.getDeliveryId());
    }

    @KafkaHandler
    public void handle(@Payload DeliveryFulfilledIntegrationEvent event) {
        log.info("Received event: {}", event);
        courierDeliveryService.fulfill(event.getDeliveryId());
    }
}
