package com.demoworks.demodelivery.delivery.tracking.domain.model;

import java.time.Duration;

public record DeliveryEstimate(Duration estimatedTime, Double distanceInKm) {}
