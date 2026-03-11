package com.leoga.ecom.order.clients;

import com.leoga.ecom.order.dto.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.Optional;

@HttpExchange("/api/users")
public interface UserServiceClient {

    @GetExchange("/{id}")
    Optional<UserResponse> getUserById(@PathVariable String id);
}
