package com.example.inventoryservice.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.example.inventoryservice.dto.OrderCreatedEvent;

@Service
public class InventoryConsumer {

    @KafkaListener(topics = "order-events", groupId = "inventory-group")
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println("[INVENTORY] Nhận sự kiện: orderId=" + event.getOrderId() +
                         ", productCode=" + event.getProductCode() +
                         ", quantity=" + event.getQuantity());
        System.out.println("[INVENTORY] Đang trừ kho cho sản phẩm: " + event.getProductCode());
    }
}

