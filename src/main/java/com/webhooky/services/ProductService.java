package com.webhooky.services;

import com.webhooky.entities.Product;
import com.webhooky.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> getProduct(String id) {
        return productRepository.findById(id);
    }
}
