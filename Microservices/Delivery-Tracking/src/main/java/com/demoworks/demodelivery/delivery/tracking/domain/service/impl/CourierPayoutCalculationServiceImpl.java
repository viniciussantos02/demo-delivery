package com.demoworks.demodelivery.delivery.tracking.domain.service.impl;

import com.demoworks.demodelivery.delivery.tracking.domain.service.CourierPayoutCalculationService;
import com.demoworks.demodelivery.delivery.tracking.infrastructure.http.clinet.CourierAPIClient;
import com.demoworks.demodelivery.delivery.tracking.infrastructure.http.clinet.exception.BadGatewayException;
import com.demoworks.demodelivery.delivery.tracking.infrastructure.http.clinet.exception.GatewayTimeoutException;
import com.demoworks.demodelivery.delivery.tracking.infrastructure.http.clinet.model.CourierPayoutCalculationInput;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculationServiceImpl implements CourierPayoutCalculationService {

    private final CourierAPIClient courierAPIClient;

    @Override
    public BigDecimal calculateCourierPayoutForDelivery(Double distanceInKm) {
        try {
            var resultModel = courierAPIClient.payoutCalculation(new CourierPayoutCalculationInput(distanceInKm));
            return resultModel.payoutFee();
        } catch (ResourceAccessException e) {
            throw new GatewayTimeoutException(e);
        } catch (HttpServerErrorException | CallNotPermittedException | IllegalArgumentException e) {
            throw new BadGatewayException(e);
        }
    }
}
