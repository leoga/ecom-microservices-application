package com.leoga.ecom.order.services;

import com.leoga.ecom.order.clients.ProductServiceClient;
import com.leoga.ecom.order.clients.UserServiceClient;
import com.leoga.ecom.order.configuration.RabbitMQData;
import com.leoga.ecom.order.dto.*;
import com.leoga.ecom.order.mappers.OrderMapper;
import com.leoga.ecom.order.model.*;
import com.leoga.ecom.order.repositories.CartItemRepository;
import com.leoga.ecom.order.repositories.OrderRepository;
import com.leoga.ecom.order.model.OrderStatus;
import com.leoga.ecom.order.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final CartItemService cartItemService;
    private final UserServiceClient userServiceClient;
    private final ProductServiceClient productServiceClient;
    private final OrderMapper orderMapper;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQData rabbitMQData;

    public OrderResponse createOrder(String userId) {
        // Validate for cart items
        List<CartItemResponse> cartItems = cartItemService.getCartItems(userId);
        if (cartItems.isEmpty()) return null;

        // Calculated total price
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItemResponse::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create order
        UserResponse user = userServiceClient.getUserById(userId).orElse(null);
        if (null == user) return null;
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalPrice);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setItems(cartItemRepository.findByUserId(userId).stream()
                .map(cartItem -> orderMapper.fromCartItemToOrderItem(cartItem, order, productServiceClient)).toList());
        orderRepository.save(order);

        // Clear cart
        cartItemService.clearCart(userId);

        rabbitTemplate.convertAndSend(rabbitMQData.getExchange().getName(),
                rabbitMQData.getRouting().getKey(),
                orderMapper.toOrderCreatedEvent(order));

        return orderMapper.toOrderResponse(order);
    }
}
