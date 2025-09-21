package com.gmontinny.springbootaiopenaitensorflow.controller;

import com.gmontinny.springbootaiopenaitensorflow.dto.ProductRequest;
import com.gmontinny.springbootaiopenaitensorflow.entity.Product;
import com.gmontinny.springbootaiopenaitensorflow.repository.ProductRepository;
import com.gmontinny.springbootaiopenaitensorflow.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService productService;
    private final ProductRepository productRepository;
    
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest request) {
        Product product = productService.createProductWithDescription(request);
        return ResponseEntity.ok(product);
    }
    
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productRepository.findByCategory(category);
        return ResponseEntity.ok(products);
    }
}