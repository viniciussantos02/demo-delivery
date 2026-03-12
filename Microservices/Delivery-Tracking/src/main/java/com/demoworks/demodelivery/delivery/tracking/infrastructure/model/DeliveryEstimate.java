package com.demoworks.demodelivery.delivery.tracking.infrastructure.model;

import java.time.Duration;

public record DeliveryEstimate(Duration estimatedTime, Double distanceInKm) {}
