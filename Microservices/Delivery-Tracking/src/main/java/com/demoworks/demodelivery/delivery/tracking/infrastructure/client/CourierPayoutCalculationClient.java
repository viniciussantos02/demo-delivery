package com.demoworks.demodelivery.delivery.tracking.infrastructure.client;

import java.math.BigDecimal;

public interface CourierPayoutCalculationClient {

    BigDecimal calculateCourierPayoutForDelivery(Double distanceInKm);
}
