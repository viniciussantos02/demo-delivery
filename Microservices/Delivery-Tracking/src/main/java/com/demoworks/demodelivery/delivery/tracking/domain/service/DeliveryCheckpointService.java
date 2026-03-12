package com.demoworks.demodelivery.delivery.tracking.domain.service;

import com.demoworks.demodelivery.delivery.tracking.domain.model.Delivery;
import com.demoworks.demodelivery.delivery.tracking.domain.model.exception.DomainException;
import com.demoworks.demodelivery.delivery.tracking.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryCheckpointService {

    private final DeliveryRepository deliveryRepository;

    public void placeDelivery(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(DomainException::new);
        delivery.place();
        deliveryRepository.saveAndFlush(delivery);
    }

    public void pickupDelivery(UUID deliveryId, UUID courierId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(DomainException::new);
        delivery.pickUp(courierId);
        deliveryRepository.saveAndFlush(delivery);
    }

    public void completeDelivery(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(DomainException::new);
        delivery.markAsDelivered();
        deliveryRepository.saveAndFlush(delivery);
    }
}
