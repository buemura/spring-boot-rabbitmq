package com.webhooky.services;

import com.webhooky.entities.Product;
import com.webhooky.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Optional<Product> getProduct(String id) {
        return productRepository.findById(id);
    }
}
