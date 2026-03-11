package com.leoga.ecom.order.clients;

import com.leoga.ecom.order.dto.ProductRequest;
import com.leoga.ecom.order.dto.ProductResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PatchExchange;

import java.util.Optional;

@HttpExchange("/api/products")
public interface ProductServiceClient {

    @GetExchange("/{id}")
    Optional<ProductResponse> getProductById(@PathVariable Long id);

    @PatchExchange("{id}")
    void updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest);

}
