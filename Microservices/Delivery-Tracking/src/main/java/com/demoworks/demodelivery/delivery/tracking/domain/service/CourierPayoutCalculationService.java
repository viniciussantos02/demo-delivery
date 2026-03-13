package com.demoworks.demodelivery.delivery.tracking.domain.service;

import java.math.BigDecimal;

public interface CourierPayoutCalculationService {

    BigDecimal calculateCourierPayoutForDelivery(Double distanceInKm);
}
