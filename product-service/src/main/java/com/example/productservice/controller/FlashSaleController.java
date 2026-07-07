package com.example.productservice.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.productservice.service.FlashSaleService;

@RestController
@RequestMapping("/api/v1/flashsale")
public class FlashSaleController {

    private final FlashSaleService flashSaleService;

    @Autowired
    public FlashSaleController(FlashSaleService flashSaleService) {
        this.flashSaleService = flashSaleService;
    }

    @PostMapping("/buy/{id}")
    public ResponseEntity<String> buyFlashSale(@PathVariable Long id) {
        for (int i = 0; i < 5; i++) {
            int userId = i + 1;
            Thread thread = new Thread(() -> {
                String result = flashSaleService.buyProduct(id, "user" + userId);
                System.out.println("[User " + userId + "] " + result);
            });
            thread.start();
        }

        return ResponseEntity.ok("Đã kích hoạt giả lập 5 người cùng mua Flash Sale! Hãy xem Console.");
    }
}

