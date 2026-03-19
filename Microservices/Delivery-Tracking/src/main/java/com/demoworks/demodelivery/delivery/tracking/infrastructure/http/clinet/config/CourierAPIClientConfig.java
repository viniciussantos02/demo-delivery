package com.demoworks.demodelivery.delivery.tracking.infrastructure.http.clinet.config;

import com.demoworks.demodelivery.delivery.tracking.infrastructure.http.clinet.CourierAPIClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class CourierAPIClientConfig {

    @Value("${courier.api.base-url}")
    private String courierApiBaseUrl;

    @Bean  //Configurando o CourierAPIClient para ser injetado em outros componentes
           // Dessa forma sempre que for injetado em outras classes ele tera essas configurações pré-definidas
    public CourierAPIClient courierAPIClient(RestClient.Builder builder) {
        RestClient restClient = builder.baseUrl(courierApiBaseUrl).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();
        return proxyFactory.createClient(CourierAPIClient.class);
    }
}
