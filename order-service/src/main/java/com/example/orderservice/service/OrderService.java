package com.example.orderservice.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import com.example.orderservice.dto.OrderCreatedEvent;
import java.util.UUID;

@Service
public class OrderService {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    @Autowired
    public OrderService(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String placeOrder(String productCode, int quantity, String email) {
        String orderId = UUID.randomUUID().toString();
        System.out.println("[DB] Lưu đơn hàng vào Database với orderId: " + orderId);

        OrderCreatedEvent event = new OrderCreatedEvent(orderId, productCode, quantity, email);
        kafkaTemplate.send("order-events", orderId, event);
        System.out.println("[KAFKA] Bắn sự kiện OrderCreatedEvent vào topic order-events");

        return "Đặt hàng thành công!";
    }
}

