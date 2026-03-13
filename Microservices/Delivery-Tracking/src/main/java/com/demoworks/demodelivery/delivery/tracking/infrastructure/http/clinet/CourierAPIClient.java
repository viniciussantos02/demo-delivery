package com.demoworks.demodelivery.delivery.tracking.infrastructure.http.clinet;

import com.demoworks.demodelivery.delivery.tracking.infrastructure.http.clinet.model.CourierPayoutCalculationInput;
import com.demoworks.demodelivery.delivery.tracking.infrastructure.http.clinet.model.CourierPayoutResultModel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/v1/couriers")
public interface CourierAPIClient {

    @PostExchange("/payout-calculation")
    CourierPayoutResultModel payoutCalculation(@RequestBody CourierPayoutCalculationInput input);
}
