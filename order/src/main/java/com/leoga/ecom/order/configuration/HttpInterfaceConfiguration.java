package com.leoga.ecom.order.configuration;

import com.leoga.ecom.order.clients.ProductServiceClient;
import com.leoga.ecom.order.clients.UserServiceClient;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpInterfaceConfiguration {

    @Autowired(required = false)
    private ObservationRegistry observationRegistry;
    @Autowired(required = false)
    private Tracer tracer;
    @Autowired(required = false)
    private Propagator propagator;

    @Bean
    @Primary
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    @LoadBalanced
    @Qualifier("loadBalanced")
    public RestClient.Builder loadBalancedRestClientBuilder() {

        RestClient.Builder builder = RestClient.builder();

        if (null != observationRegistry) {
            builder.requestInterceptor(createTracingInterceptor());
        }

        return builder
                .defaultStatusHandler(HttpStatusCode::isError,
                        ((request, response) -> {}
                        ));
    }

    private ClientHttpRequestInterceptor createTracingInterceptor() {
        return ((request, body, execution) -> {
            if (null != tracer && null != propagator
                    && null != tracer.currentSpan()) {
                propagator.inject(tracer.currentTraceContext().context(),
                        request.getHeaders(),
                        (carrier, key, value) -> carrier.add(key, value));
            }
            return execution.execute(request, body);
        }
        );
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
