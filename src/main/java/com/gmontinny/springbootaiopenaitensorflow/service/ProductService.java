package com.gmontinny.springbootaiopenaitensorflow.service;

import com.gmontinny.springbootaiopenaitensorflow.dto.ProductRequest;
import com.gmontinny.springbootaiopenaitensorflow.entity.Product;
import com.gmontinny.springbootaiopenaitensorflow.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    
    private final ProductRepository productRepository;
    private final OpenAIService openAIService;
    
    @Transactional
    public Product createProductWithDescription(ProductRequest request) {
        log.info("Criando produto: {}", request.getName());
        
        // Gerar descrição usando OpenAI
        String generatedDescription = openAIService.generateProductDescription(
            request.getName(), 
            request.getCategory(), 
            request.getPrice()
        );
        
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setGeneratedDescription(generatedDescription);
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        
        Product savedProduct = productRepository.save(product);
        log.info("Produto criado com ID: {}", savedProduct.getId());
        
        return savedProduct;
    }
}