package com.demoworks.demodelivery.delivery.tracking.domain.service.impl;

import com.demoworks.demodelivery.delivery.tracking.domain.model.ContactPoint;
import com.demoworks.demodelivery.delivery.tracking.domain.model.DeliveryEstimate;
import com.demoworks.demodelivery.delivery.tracking.domain.service.DeliveryTimeEstimationService;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class DeliveryTimeEstimationServiceMock implements DeliveryTimeEstimationService {

    @Override
    public DeliveryEstimate estimateDeliveryTime(ContactPoint sender, ContactPoint recipient) {
        return new DeliveryEstimate(Duration.ofHours(3), 3.1);
    }
}
