package com.demoworks.demodelivery.courier.management.domain.repository;

import com.demoworks.demodelivery.courier.management.domain.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourierRepository extends JpaRepository<Courier, UUID> {

    //Metodo retorna o primeiro entregador disponível, ou seja, aquele que tem a data da última entrega cumprida mais antiga
    Optional<Courier> findTop1ByOrderByLastFulfilledDeliveryAtAsc();

    //Metodo retorna o entregador que tem a entrega pendente com o id fornecido
    Optional<Courier> findByPendingDeliveries_id(UUID deliveryId);
}
