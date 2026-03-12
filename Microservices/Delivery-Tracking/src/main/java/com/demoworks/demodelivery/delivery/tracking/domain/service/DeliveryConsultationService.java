package com.demoworks.demodelivery.delivery.tracking.domain.service;

import com.demoworks.demodelivery.delivery.tracking.domain.model.Delivery;
import com.demoworks.demodelivery.delivery.tracking.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryConsultationService {

    private final DeliveryRepository deliveryRepository;

    public Delivery findDeliveryById(UUID deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    public PagedModel<Delivery> findAllDeliveries(Pageable pageable) {
        return new PagedModel<>(deliveryRepository.findAll(pageable));
    }

}
