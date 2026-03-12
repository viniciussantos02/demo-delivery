package com.demoworks.demodelivery.delivery.tracking.infrastructure.client.impl;

import com.demoworks.demodelivery.delivery.tracking.domain.model.ContactPoint;
import com.demoworks.demodelivery.delivery.tracking.infrastructure.client.DeliveryTimeEstimationClient;
import com.demoworks.demodelivery.delivery.tracking.infrastructure.model.DeliveryEstimate;

import java.time.Duration;

public class DeliveryTimeEstimationClientMock implements DeliveryTimeEstimationClient {

    @Override
    public DeliveryEstimate estimateDeliveryTime(ContactPoint sender, ContactPoint recipient) {
        return new DeliveryEstimate(Duration.ofHours(3), 3.1);
    }
}
