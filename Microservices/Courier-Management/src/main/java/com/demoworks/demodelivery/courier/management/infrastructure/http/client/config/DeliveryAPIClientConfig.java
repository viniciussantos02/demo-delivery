package com.demoworks.demodelivery.courier.management.infrastructure.http.client.config;

import com.demoworks.demodelivery.courier.management.infrastructure.http.client.DeliveryAPIClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class DeliveryAPIClientConfig {

    @Value("${delivery.api.base-instance-url}")
    private String courierApiBaseInstanceUrl;

    @Bean
    @LoadBalanced // Habilita o balanceamento de carga para o RestClient, permitindo que ele resolva o nome do serviço e distribua as requisições entre as instâncias disponíveis
    public RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder();
    }

           //Configurando o DeliveryApi para ser injetado em outros componentes
    @Bean  // Dessa forma sempre que for injetado em outras classes ele tera essas configurações pré-definidas
    public DeliveryAPIClient courierAPIClient(RestClient.Builder builder) {
        RestClient restClient = builder.baseUrl(courierApiBaseInstanceUrl).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();
        return proxyFactory.createClient(DeliveryAPIClient.class);
    }
}
