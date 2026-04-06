package com.demoworks.demodelivery.delivery.tracking.infrastructure.event;

import com.demoworks.demodelivery.delivery.tracking.domain.event.DeliveryFulfilledEvent;
import com.demoworks.demodelivery.delivery.tracking.domain.event.DeliveryPickedUpEvent;
import com.demoworks.demodelivery.delivery.tracking.domain.event.DeliveryPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.demoworks.demodelivery.delivery.tracking.infrastructure.kafka.KafkaTopicConfig.DELIVERY_EVENTS_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryDomainEventHandler {

    private final IntegrationEventPublisher integrationEventPublisher;

                    // Esse método é um listener de eventos, ou seja, ele será chamado
    @EventListener  // sempre que um evento do tipo DeliveryPlacedEvent for publicado/registrado
    public void handle(DeliveryPlacedEvent event) {
        log.info(event.toString());
        integrationEventPublisher.publish(event, event.deliveryId().toString(), DELIVERY_EVENTS_TOPIC);
    }

    @EventListener
    public void handle(DeliveryPickedUpEvent event) {
        log.info(event.toString());
        integrationEventPublisher.publish(event, event.deliveryId().toString(), DELIVERY_EVENTS_TOPIC);
    }

    @EventListener
    public void handle(DeliveryFulfilledEvent event) {
        log.info(event.toString());
        integrationEventPublisher.publish(event, event.deliveryId().toString(), DELIVERY_EVENTS_TOPIC);
    }
}
