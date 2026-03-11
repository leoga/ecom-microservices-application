package com.leoga.ecom.order.configuration;

import com.leoga.ecom.order.clients.ProductServiceClient;
import com.leoga.ecom.order.clients.UserServiceClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Optional;

@Configuration
public class HttpInterfaceConfiguration {

    @Bean
    @Primary
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    @LoadBalanced
    @Qualifier("loadBalanced")
    public RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder()
                .defaultStatusHandler(HttpStatusCode::isError,
                        ((request, response) -> {}
                        ));
    }

    @Bean
    public UserServiceClient userHttpInterface(@Qualifier("loadBalanced") RestClient.Builder restClientBuilder) {

        RestClient restClient = restClientBuilder.baseUrl("http://user-service").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(UserServiceClient.class);

    }

    @Bean
    public ProductServiceClient productHttpInterface(@Qualifier("loadBalanced") RestClient.Builder restClientBuilder) {

        RestClient restClient = restClientBuilder.baseUrl("http://product-service").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(ProductServiceClient.class);
    }
}
