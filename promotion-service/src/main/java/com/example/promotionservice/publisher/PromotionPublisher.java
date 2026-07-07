package com.example.promotionservice.publisher;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.dto.PromotionEvent;

@Service
public class PromotionPublisher {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public PromotionPublisher(StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishPromotionUpdate(Long productId) {
        try {
            PromotionEvent event = new PromotionEvent(productId);
            String message = objectMapper.writeValueAsString(event);
            stringRedisTemplate.convertAndSend("promotion-updates", message);
            System.out.println("[PUBLISHER] Bắn sự kiện xóa cache cho productId: " + productId);
        } catch (Exception e) {
            System.err.println("[PUBLISHER ERROR] " + e.getMessage());
        }
    }
}

