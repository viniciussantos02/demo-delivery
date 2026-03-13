package com.demoworks.demodelivery.delivery.tracking.domain.service.impl;

import com.demoworks.demodelivery.delivery.tracking.domain.service.CourierPayoutCalculationService;
import com.demoworks.demodelivery.delivery.tracking.infrastructure.http.clinet.CourierAPIClient;
import com.demoworks.demodelivery.delivery.tracking.infrastructure.http.clinet.model.CourierPayoutCalculationInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculationServiceImpl implements CourierPayoutCalculationService {

    private final CourierAPIClient courierAPIClient;

    @Override
    public BigDecimal calculateCourierPayoutForDelivery(Double distanceInKm) {
        var resultModel = courierAPIClient.payoutCalculation(new CourierPayoutCalculationInput(distanceInKm));

        return resultModel.payoutFee();
    }
}
