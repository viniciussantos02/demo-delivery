package com.demoworks.demodelivery.courier.management.infrastructure.event;

import java.time.OffsetDateTime;
import java.util.UUID;

public record DeliveryPickUpIntegrationEvent (OffsetDateTime occurredAt, UUID deliveryId, UUID courierId) {}
