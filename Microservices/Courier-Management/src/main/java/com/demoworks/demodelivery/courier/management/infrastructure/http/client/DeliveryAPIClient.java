package com.demoworks.demodelivery.courier.management.infrastructure.http.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.UUID;

@HttpExchange("/api/v1/deliveries")
public interface DeliveryAPIClient {

    @PutExchange("/{deliveryId}/add-delivery-courier")
    DeliveryDTO editDelivery(@RequestBody DeliveryDTO input, @PathVariable UUID deliveryId);
}
