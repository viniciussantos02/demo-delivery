package com.demoworks.demodelivery.delivery.tracking.domain.service;

import com.demoworks.demodelivery.delivery.tracking.domain.model.Delivery;
import com.demoworks.demodelivery.delivery.tracking.domain.model.exception.DomainException;
import com.demoworks.demodelivery.delivery.tracking.domain.repository.DeliveryRepository;
import com.demoworks.demodelivery.delivery.tracking.infrastructure.http.clinet.CourierAPIClient;
import com.demoworks.demodelivery.delivery.tracking.infrastructure.http.clinet.model.CourierDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryCheckpointService {

    private final DeliveryRepository deliveryRepository;

    private final CourierAPIClient courierApiClient;

    public void placeDelivery(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(DomainException::new);
        delivery.place();
        deliveryRepository.saveAndFlush(delivery);
    }

    public void pickupDelivery(UUID deliveryId, UUID courierId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(DomainException::new);

        if (courierId != null) { //TODO pode ser adicionado um try catch aqui para lidar com erros retornados do servico de courier
            CourierDTO courier = courierApiClient.findCourierById(courierId);
            delivery.pickUp(courier.id());
            log.info("Courier {} picked up the delivery {}", courier.id(), deliveryId);
            deliveryRepository.saveAndFlush(delivery);

            return;
        }

        delivery.pickUp(null);
        deliveryRepository.saveAndFlush(delivery);
    }

    public void completeDelivery(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(DomainException::new);
        delivery.markAsDelivered();
        deliveryRepository.saveAndFlush(delivery);
    }
}
