package com.demoworks.demodelivery.courier.management.domain.repository;

import com.demoworks.demodelivery.courier.management.domain.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourierRepository extends JpaRepository<Courier, UUID> {
}
