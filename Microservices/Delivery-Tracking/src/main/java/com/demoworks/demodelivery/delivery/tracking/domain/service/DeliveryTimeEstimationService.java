package com.demoworks.demodelivery.delivery.tracking.domain.service;

import com.demoworks.demodelivery.delivery.tracking.domain.model.DeliveryEstimate;
import com.demoworks.demodelivery.delivery.tracking.domain.model.ContactPoint;
import org.springframework.stereotype.Service;

public interface DeliveryTimeEstimationService {

    DeliveryEstimate estimateDeliveryTime(ContactPoint sender, ContactPoint recipient);
}
