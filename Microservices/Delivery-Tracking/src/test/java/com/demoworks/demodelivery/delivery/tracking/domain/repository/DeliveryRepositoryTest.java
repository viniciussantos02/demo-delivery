package com.demoworks.demodelivery.delivery.tracking.domain.repository;

import com.demoworks.demodelivery.delivery.tracking.domain.model.ContactPoint;
import com.demoworks.demodelivery.delivery.tracking.domain.model.Delivery;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DeliveryRepositoryTest {

    @Autowired
    private DeliveryRepository repository;

    @Test
    public void shouldPersist() {
        Delivery delivery = Delivery.draft();
        delivery.editPreparationDetails(createValidPreparationDetails());
        delivery.addItem("Computador", 3);
        delivery.addItem("PS5", 5);

        repository.saveAndFlush(delivery);

        Delivery persistedDelivery = repository.findById(delivery.getId()).orElseThrow();

        assertEquals(2, persistedDelivery.getItems().size());
    }

    private Delivery.PreparationDetails createValidPreparationDetails() {
        ContactPoint sender = ContactPoint.builder()
                .zipCode("00000-000")
                .street("Rua Sao Paulo")
                .number("100")
                .complement("Sala 401")
                .name("Joao Silva")
                .phone("(11) 90000-1234")
                .build();

        ContactPoint recipient = ContactPoint.builder()
                .zipCode("01234-000")
                .street("Rua Brasil")
                .number("120")
                .complement("Casa")
                .name("Maria Silva")
                .phone("(11) 92345-1234")
                .build();

        return Delivery.PreparationDetails.builder()
                .sender(sender)
                .recipient(recipient)
                .distanceFee(new BigDecimal("15.00"))
                .courierPayout(new BigDecimal("5.00"))
                .expectedDeliveryTime(Duration.ofHours(5))
                .build();
    }
}