package com.leoga.ecom.notification.services;


import com.leoga.ecom.notification.payload.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Slf4j
public class OrderEventConsumer {

//    @RabbitListener(queues = "${rabbitmq.queue.name}")
//    public void handleOrderEvent(OrderCreatedEvent orderEvent) {
//        System.out.println("Received Order Event: " + orderEvent);
//
//        Long orderId = orderEvent.getId();
//        OrderStatus orderStatus = orderEvent.getStatus();
//
//        System.out.println("Received Order ID: " + orderId);
//        System.out.println("Received Order Status: " + orderStatus);
//
//        // Update Database
//        // Send Notification
//        // Send Emails
//        // Generate Invoice
//        // Send Seller Notification
//    }

    @Bean
    public Consumer<OrderCreatedEvent> orderCreated() {
        return event -> {
            log.info("Received order Created Event for order: {}", event.getId());
            log.info("Received order Created Event for user id: {}", event.getUserId());
        };
    }
}
