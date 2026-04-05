package com.demoworks.demodelivery.courier.management.domain.repository;

import com.demoworks.demodelivery.courier.management.domain.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourierRepository extends JpaRepository<Courier, UUID> {

    //Metodo retorna o entregador que fez a ultima entrega ha mais tempo, priorizando o que tiver a data null (quem ainda nao completou nenhuma entrega) e que tambem nao tem entregas pendentes
    @Query(value = """
        SELECT *\s
                 FROM courier c
                 WHERE c.pending_deliveries_quantity = 0
                 ORDER BY
                     CASE WHEN c.last_fulfilled_delivery_at IS NULL THEN 0 ELSE 1 END,
                     c.last_fulfilled_delivery_at ASC
                 LIMIT 1;
   """, nativeQuery = true)
    Optional<Courier> findTop1ByOrderByLastFulfilledDeliveryAtAscPrioritisingNullAndZeroPendingDeliveries();

    //Metodo retorna o entregador que tem a entrega pendente com o id fornecido
    Optional<Courier> findByPendingDeliveries_id(UUID deliveryId);
}
