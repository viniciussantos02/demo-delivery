package com.demoworks.demodelivery.courier.management.infrastructure.event;

import java.time.OffsetDateTime;
import java.util.UUID;

public record DeliveryPlacedIntegrationEvent(OffsetDateTime occurredAt, UUID deliveryId) {}
