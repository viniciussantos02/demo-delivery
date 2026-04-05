package com.demoworks.demodelivery.courier.management.domain.service;

import com.demoworks.demodelivery.courier.management.domain.model.Courier;
import com.demoworks.demodelivery.courier.management.domain.repository.CourierRepository;
import com.demoworks.demodelivery.courier.management.infrastructure.http.client.DeliveryAPIClient;
import com.demoworks.demodelivery.courier.management.infrastructure.http.client.DeliveryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CourierDeliveryService {

    private final CourierRepository courierRepository;
    private final DeliveryAPIClient deliveryAPIClient;

    public void assign(UUID deliveryId, UUID courierId) {
        Courier courier = findCourier(courierId);

        courier.assign(deliveryId);

        if (courierId == null) {
            //TODO pode ser adicionado um try catch aqui para lidar com erros retornados do servico de delivery, e nesse nao atribuir a entrega ao courier
            var delivery = deliveryAPIClient.editDelivery(new DeliveryDTO(courier.getId()), deliveryId);
            log.info("Delivery {} status updated to IN_TRANSIT with courier {}", deliveryId, delivery.courierId());
        }

        courierRepository.saveAndFlush(courier);

        log.info("Courier {} assigned to delivery {}", courier.getId(), deliveryId);
    }

    public void fulfill(UUID deliveryId) {
        Courier courier = courierRepository.findByPendingDeliveries_id(deliveryId).orElseThrow();

        courier.fulfill(deliveryId);

        courierRepository.saveAndFlush(courier);

        log.info("Courier {} fulfilled the delivery {}", courier.getId(), deliveryId);
    }

    private Courier findCourier(UUID courierId) {
        if (courierId != null) {
            return courierRepository.findById(courierId).orElseThrow();
        } else {
            log.info("No courierId provided, finding the next available courier");
            return courierRepository.findTop1ByOrderByLastFulfilledDeliveryAtAscPrioritisingNullAndZeroPendingDeliveries()
                    .orElseThrow(() -> new RuntimeException("No available couriers found"));
        }
    }
}
