package com.example.productservice.subscriber;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.productservice.dto.PromotionEvent;

@Service
public class PromotionSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final CacheManager cacheManager;

    @Autowired
    public PromotionSubscriber(ObjectMapper objectMapper, CacheManager cacheManager) {
        this.objectMapper = objectMapper;
        this.cacheManager = cacheManager;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String messageBody = new String(message.getBody());
            PromotionEvent event = objectMapper.readValue(messageBody, PromotionEvent.class);

            var productsCache = cacheManager.getCache("products");
            if (productsCache != null) {
                productsCache.evict(event.getProductId());
                System.out.println("[SUBSCRIBER] Đã xóa cache cho productId: " + event.getProductId());
            } else {
                System.out.println("[SUBSCRIBER] Cache 'products' không tồn tại");
            }
        } catch (Exception e) {
            System.err.println("[SUBSCRIBER ERROR] " + e.getMessage());
        }
    }
}

