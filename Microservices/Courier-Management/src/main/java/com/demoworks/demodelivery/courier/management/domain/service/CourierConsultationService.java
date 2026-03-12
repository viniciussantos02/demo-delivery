package com.demoworks.demodelivery.courier.management.domain.service;

import com.demoworks.demodelivery.courier.management.domain.model.Courier;
import com.demoworks.demodelivery.courier.management.domain.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourierConsultationService {

    private final CourierRepository courierRepository;

    public PagedModel<Courier> findAllCouriers(Pageable pageable) {
        return new PagedModel<>(courierRepository.findAll(pageable));
    }

    public Courier findCourierById(UUID courierId) {
        return courierRepository.findById(courierId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY));
    }
}
