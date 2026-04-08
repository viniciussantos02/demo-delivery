package com.demoworks.demodelivery.courier.management.infrastructure.http.client.config;

import com.demoworks.demodelivery.courier.management.infrastructure.http.client.DeliveryAPIClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class DeliveryAPIClientConfig {

    @Value("${delivery.api.base-instance-url}")
    private String courierApiBaseInstanceUrl;

    @Bean
    @LoadBalanced // Habilita o load balance para o RestClient, permitindo que ele resolva o nome do serviço e distribua as requisições entre as instâncias disponíveis
    public RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder();
    }

    //Configurando o DeliveryApi para ser injetado em outros componentes
    @Bean  // Dessa forma sempre que for injetado em outras classes ele tera essas configurações pré-definidas
    public DeliveryAPIClient courierAPIClient(RestClient.Builder builder) {
        RestClient restClient = builder.baseUrl(courierApiBaseInstanceUrl)
                .requestFactory(generateClientRequestFactory())
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();
        return proxyFactory.createClient(DeliveryAPIClient.class);
    }

    //Configuracao do timeout pattern para o RestTemplate
    private ClientHttpRequestFactory generateClientRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofMillis(10));
        factory.setReadTimeout(Duration.ofMillis(200));
        return factory;
    }
}