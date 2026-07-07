package com.example.productservice.service;

import org.redisson.api.RedissonClient;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import java.util.concurrent.TimeUnit;

@Service
public class FlashSaleService {

    private final RedissonClient redissonClient;
    private final ProductRepository productRepository;

    @Autowired
    public FlashSaleService(RedissonClient redissonClient, ProductRepository productRepository) {
        this.redissonClient = redissonClient;
        this.productRepository = productRepository;
    }

    public String buyProduct(Long productId, String userId) {
        RLock lock = redissonClient.getLock("lock:product:" + productId);
        try {
            boolean isLocked = lock.tryLock(5, 10, TimeUnit.SECONDS);
            if (isLocked) {
                Product product = productRepository.findById(productId).orElse(null);
                if (product == null) {
                    return "Sản phẩm không tồn tại";
                }
                if (product.getStockQuantity() > 0) {
                    product.setStockQuantity(product.getStockQuantity() - 1);
                    productRepository.save(product);
                    return "Mua hàng thành công! Số lượng còn lại: " + product.getStockQuantity();
                } else {
                    return "Hết hàng!";
                }
            } else {
                return "Không thể lấy khóa, hãy thử lại";
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Lỗi gián đoạn: " + e.getMessage();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}

