package com.gmontinny.springbootaiopenaitensorflow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChatRequest {
    @NotBlank(message = "Mensagem não pode estar vazia")
    private String message;
}