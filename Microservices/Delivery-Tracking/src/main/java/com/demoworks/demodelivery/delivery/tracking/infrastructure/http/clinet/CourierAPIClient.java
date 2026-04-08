package com.demoworks.demodelivery.delivery.tracking.infrastructure.http.clinet;

import com.demoworks.demodelivery.delivery.tracking.infrastructure.http.clinet.model.CourierDTO;
import com.demoworks.demodelivery.delivery.tracking.infrastructure.http.clinet.model.CourierPayoutCalculationInput;
import com.demoworks.demodelivery.delivery.tracking.infrastructure.http.clinet.model.CourierPayoutResultModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.UUID;

@HttpExchange("/api/v1/couriers")
public interface CourierAPIClient {

    @PostExchange("/payout-calculation")
    @Retry(name = "Retry_CourierAPIClient_payoutCalculation")
    @CircuitBreaker(name = "CircuitBreaker_CourierAPIClient_payoutCalculation")
    CourierPayoutResultModel payoutCalculation(@RequestBody CourierPayoutCalculationInput input);

    @GetExchange("/{courierId}")
    CourierDTO findCourierById(@PathVariable UUID courierId);
}
