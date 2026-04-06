package com.demoworks.demodelivery.courier.management.infrastructure.event;

import java.time.OffsetDateTime;
import java.util.UUID;

public record DeliveryFulfilledIntegrationEvent(OffsetDateTime occurredAt, UUID deliveryId) {}
