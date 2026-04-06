package com.demoworks.demodelivery.delivery.tracking.domain.service;

import com.demoworks.demodelivery.delivery.tracking.api.model.ContactPointDTO;
import com.demoworks.demodelivery.delivery.tracking.api.model.DeliveryDTO;
import com.demoworks.demodelivery.delivery.tracking.api.model.DeliveryDetailsDTO;
import com.demoworks.demodelivery.delivery.tracking.domain.model.ContactPoint;
import com.demoworks.demodelivery.delivery.tracking.domain.model.Delivery;
import com.demoworks.demodelivery.delivery.tracking.domain.model.exception.DomainException;
import com.demoworks.demodelivery.delivery.tracking.domain.repository.DeliveryRepository;
import com.demoworks.demodelivery.delivery.tracking.domain.model.DeliveryEstimate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryPreparationService {

    private final DeliveryRepository deliveryRepository;

    private final DeliveryTimeEstimationService deliveryTimeEstimationService;
    private final CourierPayoutCalculationService courierPayoutCalculationService;

    //@Transactional garante que a operacao de persistencia seja executada dentro de uma transacao,
    //garantindo a consistencia dos dados e permitindo rollback em caso de falhas.
    //Entao é feito de forma atomica, ou seja, ou tudo é executado com sucesso ou nada é persistido.
    @Transactional
    public Delivery draftDelivery(DeliveryDTO input) {
        Delivery delivery = Delivery.draft();
        handlePrepartion(delivery, input);

        //saveAndFlush é um método do Spring Data JPA que salva a entidade e imediatamente a sincroniza com o banco de dados,
        //garantindo que as alterações sejam persistidas e visíveis para outras transações.
        return deliveryRepository.saveAndFlush(delivery);
    }

    @Transactional
    public Delivery editDelivery(UUID deliveryId, DeliveryDTO input) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(DomainException::new);

        delivery.removeItems();
        handlePrepartion(delivery, input);
        return deliveryRepository.saveAndFlush(delivery);
    }

    @Transactional
    public Delivery addDeliveryCourier(UUID deliveryId, UUID courierId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(DomainException::new);

        log.info("Adding courier {} to delivery {}", courierId, deliveryId);
        delivery.addDeliveryCourier(courierId);
        return deliveryRepository.saveAndFlush(delivery);
    }


    public Delivery editDelivery(Delivery delivery, DeliveryDTO input) {
        delivery.removeItems();
        handlePrepartion(delivery, input);
        return deliveryRepository.saveAndFlush(delivery);
    }

    private void handlePrepartion(Delivery delivery, DeliveryDTO input) {
        ContactPointDTO senderInput = input.sender();
        ContactPointDTO recipientInput = input.recipient();

        ContactPoint sender = ContactPoint.builder()
                .phone(senderInput.phone())
                .name(senderInput.name())
                .complement(senderInput.complement())
                .number(senderInput.number())
                .zipCode(senderInput.zipCode())
                .street(senderInput.street())
                .build();

        ContactPoint recipient = ContactPoint.builder()
                .phone(recipientInput.phone())
                .name(recipientInput.name())
                .complement(recipientInput.complement())
                .number(recipientInput.number())
                .zipCode(recipientInput.zipCode())
                .street(recipientInput.street())
                .build();

        DeliveryEstimate deliveryEstimate = deliveryTimeEstimationService.estimateDeliveryTime(sender, recipient);

        BigDecimal payoutForDelivery = courierPayoutCalculationService.calculateCourierPayoutForDelivery(deliveryEstimate.distanceInKm());

        BigDecimal distanceFee = calculateDistanceFee(deliveryEstimate.distanceInKm());

        var preparationDetails = Delivery.PreparationDetails.builder()
                .recipient(recipient)
                .sender(sender)
                .expectedDeliveryTime(deliveryEstimate.estimatedTime())
                .courierPayout(payoutForDelivery)
                .distanceFee(distanceFee)
                .build();

        delivery.editPreparationDetails(preparationDetails);

        input.items().forEach(item -> {
            delivery.addItem(item.name(), item.quantity());
        });

    }

    private BigDecimal calculateDistanceFee(Double distanceInKm) {
        return new BigDecimal("3").multiply(new BigDecimal(distanceInKm))
                .setScale(2, RoundingMode.HALF_EVEN);
    }
}
