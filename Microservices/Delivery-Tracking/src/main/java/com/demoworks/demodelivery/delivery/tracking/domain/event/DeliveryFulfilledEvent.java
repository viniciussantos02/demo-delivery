package com.demoworks.demodelivery.delivery.tracking.domain.event;

import java.time.OffsetDateTime;
import java.util.UUID;

/* Essa é uma classe de evento da Delivery */
public record DeliveryFulfilledEvent(OffsetDateTime occurredAt, UUID deliveryId) {}
