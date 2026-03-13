package com.demoworks.demodelivery.courier.management.domain.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CourierPayoutService {

    public BigDecimal calculatePayout(Double aDouble) {
        return new BigDecimal("10").multiply(new BigDecimal(aDouble))
                .setScale(2, RoundingMode.HALF_EVEN);
    }
}
