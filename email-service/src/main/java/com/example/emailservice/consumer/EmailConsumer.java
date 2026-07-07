package com.example.emailservice.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.example.emailservice.dto.OrderCreatedEvent;

@Service
public class EmailConsumer {

    @KafkaListener(topics = "order-events", groupId = "email-group")
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println("[EMAIL] Nhận sự kiện: orderId=" + event.getOrderId());
        System.out.println("[EMAIL] Đang gửi thông báo đến email: " + event.getCustomerEmail());
    }
}

