package com.example.promotionservice.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.promotionservice.publisher.PromotionPublisher;

@RestController
@RequestMapping("/api/v1/promotions")
public class PromotionController {

    private final PromotionPublisher promotionPublisher;

    @Autowired
    public PromotionController(PromotionPublisher promotionPublisher) {
        this.promotionPublisher = promotionPublisher;
    }

    @PostMapping("/update-price/{productId}")
    public ResponseEntity<String> updatePrice(@PathVariable Long productId) {
        promotionPublisher.publishPromotionUpdate(productId);
        return ResponseEntity.ok("Đã công bố khuyến mãi mới cho sản phẩm ID: " + productId);
    }
}

