package com.demoworks.demodelivery.delivery.tracking.api.controller;


import com.demoworks.demodelivery.delivery.tracking.api.model.DeliveryDTO;
import com.demoworks.demodelivery.delivery.tracking.api.model.DeliveryDetailsDTO;
import com.demoworks.demodelivery.delivery.tracking.domain.model.Delivery;
import com.demoworks.demodelivery.delivery.tracking.domain.service.DeliveryCheckpointService;
import com.demoworks.demodelivery.delivery.tracking.domain.service.DeliveryConsultationService;
import com.demoworks.demodelivery.delivery.tracking.domain.service.DeliveryPreparationService;
import jakarta.annotation.Nullable;
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
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

    private final DeliveryPreparationService deliveryPreparationService;
    private final DeliveryCheckpointService deliveryCheckpointService;
    private final DeliveryConsultationService deliveryConsultationService;

    @PostMapping
    public ResponseEntity<Delivery> draftDelivery(@Valid @RequestBody DeliveryDTO input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(deliveryPreparationService.draftDelivery(input));
    }

    @PutMapping("/{deliveryId}")
    public ResponseEntity<Delivery> editDelivery(@PathVariable UUID deliveryId, @Valid @RequestBody DeliveryDTO input) {
        return ResponseEntity.ok(deliveryPreparationService.editDelivery(deliveryId, input));
    }

    @PutMapping("/{deliveryId}/add-delivery-courier")
    public ResponseEntity<Delivery> addDeliveryCourier(@PathVariable UUID deliveryId, @Valid @RequestBody DeliveryDetailsDTO input) {
        return ResponseEntity.ok(deliveryPreparationService.addDeliveryCourier(deliveryId, input.courierId()));
    }
                          //Recursos de paginanção do Spring Data, como Pageable e PagedModel,
                          // permitem que os clientes solicitem dados em partes, especificando o número da página e o tamanho da página.
    @GetMapping           // Isso é útil para lidar com grandes conjuntos de dados, evitando sobrecarregar o cliente ou o servidor.
    public ResponseEntity<PagedModel<Delivery>> findallDeliveries(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(deliveryConsultationService.findAllDeliveries(pageable));
    }

    @GetMapping("/{deliveryId}")
    public ResponseEntity<Delivery> findDeliveryById(@PathVariable UUID deliveryId) {
        return ResponseEntity.ok(deliveryConsultationService.findDeliveryById(deliveryId));
    }

    @PostMapping("/{deliveryId}/placement")
    public ResponseEntity<Void> placeDelivery(@PathVariable UUID deliveryId) {
        deliveryCheckpointService.placeDelivery(deliveryId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{deliveryId}/pickups")
    public ResponseEntity<Void> pickupDelivery(@PathVariable UUID deliveryId, @RequestParam @Nullable UUID courierId) {
        deliveryCheckpointService.pickupDelivery(deliveryId, courierId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{deliveryId}/completion")
    public ResponseEntity<Void> completeDelivery(@PathVariable UUID deliveryId) {
        deliveryCheckpointService.completeDelivery(deliveryId);
        return ResponseEntity.ok().build();
    }
}
