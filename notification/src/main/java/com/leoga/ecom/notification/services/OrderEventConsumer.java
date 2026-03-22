package com.leoga.ecom.notification.services;

import com.leoga.ecom.notification.payload.OrderCreatedEvent;
import com.leoga.ecom.notification.payload.OrderStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void handleOrderEvent(OrderCreatedEvent orderEvent) {
        System.out.println("Received Order Event: " + orderEvent);

        Long orderId = orderEvent.getId();
        OrderStatus orderStatus = orderEvent.getStatus();

        System.out.println("Received Order ID: " + orderId);
        System.out.println("Received Order Status: " + orderStatus);

        // Update Database
        // Send Notification
        // Send Emails
        // Generate Invoice
        // Send Seller Notification
    }
}
