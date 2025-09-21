package com.gmontinny.springbootaiopenaitensorflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductRequest {
    @NotBlank(message = "Nome do produto é obrigatório")
    private String name;
    
    private String description;
    
    @Positive(message = "Preço deve ser positivo")
    private Double price;
    
    @NotBlank(message = "Categoria é obrigatória")
    private String category;
}