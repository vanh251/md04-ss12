package com.example.productservice.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.model.Product;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Cacheable(value = "products", key = "#id")
    public Product getProductById(Long id) {
        System.out.println("[DB CALL] Đọc Database cho sản phẩm ID: " + id);
        return productRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = "products", key = "#id")
    public Product updateProduct(Long id, Product productDetails) {
        System.out.println("[EVICT] Xóa Cache và cập nhật sản phẩm ID: " + id);
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setImageUrl(productDetails.getImageUrl());
        product.setPrice(productDetails.getPrice());
        return productRepository.save(product);
    }

}

