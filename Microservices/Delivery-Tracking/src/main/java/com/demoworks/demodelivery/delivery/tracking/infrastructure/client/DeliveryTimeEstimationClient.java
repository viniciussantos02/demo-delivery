package com.demoworks.demodelivery.delivery.tracking.infrastructure.client;

import com.demoworks.demodelivery.delivery.tracking.infrastructure.model.DeliveryEstimate;
import com.demoworks.demodelivery.delivery.tracking.domain.model.ContactPoint;
import org.springframework.stereotype.Service;

@Service
public interface DeliveryTimeEstimationClient {

    DeliveryEstimate estimateDeliveryTime(ContactPoint sender, ContactPoint recipient);
}
