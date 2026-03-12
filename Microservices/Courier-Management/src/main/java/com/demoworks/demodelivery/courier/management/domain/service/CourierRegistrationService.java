package com.demoworks.demodelivery.courier.management.domain.service;

import com.demoworks.demodelivery.courier.management.api.model.CourierDTO;
import com.demoworks.demodelivery.courier.management.domain.model.Courier;
import com.demoworks.demodelivery.courier.management.domain.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CourierRegistrationService {

    private final CourierRepository courierRepository;

    public Courier createRegistration(CourierDTO courierInput) {
        Courier courier = Courier.brandNew(courierInput.name(), courierInput.phone());

        return courierRepository.saveAndFlush(courier);
    }

    public Courier updateCourier(UUID courierId, CourierDTO courierInput) {
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY));

        courier.setName(courierInput.name());
        courier.setPhone(courierInput.phone());

        return courierRepository.saveAndFlush(courier);
    }
}
