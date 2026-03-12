package com.demoworks.demodelivery.delivery.tracking.domain.service;

import com.demoworks.demodelivery.delivery.tracking.api.model.ContactPointDTO;
import com.demoworks.demodelivery.delivery.tracking.api.model.DeliveryDTO;
import com.demoworks.demodelivery.delivery.tracking.domain.model.ContactPoint;
import com.demoworks.demodelivery.delivery.tracking.domain.model.Delivery;
import com.demoworks.demodelivery.delivery.tracking.domain.model.exception.DomainException;
import com.demoworks.demodelivery.delivery.tracking.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryPreparationService {

    private final DeliveryRepository deliveryRepository;

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

    public PagedModel<Delivery> findAllDeliveries(Pageable pageable) {
        return new PagedModel<>(deliveryRepository.findAll(pageable));
    }

    public Delivery findDeliveryById(UUID deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY));
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

        Duration expectedDeliveryTime = Duration.ofHours(3);

        BigDecimal distanceFee = new BigDecimal("10");
        BigDecimal payout = new BigDecimal("10");

        var preparationDetails = Delivery.PreparationDetails.builder()
                .recipient(recipient)
                .sender(sender)
                .expectedDeliveryTime(expectedDeliveryTime)
                .courierPayout(payout)
                .distanceFee(distanceFee)
                .build();

        delivery.editPreparationDetails(preparationDetails);

        input.items().forEach(item -> {
            delivery.addItem(item.name(), item.quantity());
        });

    }
}
