package com.demoworks.demodelivery.courier.management.api.controller;

import com.demoworks.demodelivery.courier.management.api.model.CourierDTO;
import com.demoworks.demodelivery.courier.management.domain.model.Courier;
import com.demoworks.demodelivery.courier.management.domain.service.CourierConsultationService;
import com.demoworks.demodelivery.courier.management.domain.service.CourierRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/couriers")
public class CourierController {

    private final CourierRegistrationService courierRegistrationService;
    private final CourierConsultationService courierConsultationService;

    @PostMapping
    public ResponseEntity<Courier> createCourier(@Valid @RequestBody CourierDTO courierInput) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courierRegistrationService.createRegistration(courierInput));
    }

    @PutMapping("/{courierId}")
    public ResponseEntity<Courier> updateCourier(@PathVariable UUID courierId, @Valid @RequestBody CourierDTO courierInput) {
        return ResponseEntity.ok(courierRegistrationService.updateCourier(courierId, courierInput));
    }

    @GetMapping
    public ResponseEntity<PagedModel<Courier>> findAllCouriers(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(courierConsultationService.findAllCouriers(pageable));
    }

    @GetMapping("/{courierId}")
    public ResponseEntity<Courier> findCourierById(@PathVariable UUID courierId) {
        return ResponseEntity.ok(courierConsultationService.findCourierById(courierId));
    }
}
