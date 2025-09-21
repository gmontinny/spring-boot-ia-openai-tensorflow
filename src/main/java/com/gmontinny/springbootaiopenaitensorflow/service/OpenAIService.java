package com.gmontinny.springbootaiopenaitensorflow.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OpenAIService {
    
    private final ChatClient chatClient;
    
    public String summarizeText(String text) {
        return chatClient.prompt()
                .user("Resuma o seguinte texto em português de forma concisa: " + text)
                .call()
                .content();
    }
    
    public String generateAutoResponse(String message) {
        return chatClient.prompt()
                .user("Gere uma resposta automática profissional e útil para: " + message)
                .call()
                .content();
    }
    
    public String generateProductDescription(String name, String category, Double price) {
        String prompt = String.format(
            "Gere uma descrição atrativa para o produto '%s' da categoria '%s' com preço R$ %.2f. " +
            "A descrição deve ser persuasiva e destacar benefícios.",
            name, category, price
        );
        
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
    
    public String translateText(String text, String targetLanguage) {
        return chatClient.prompt()
                .user("Traduza o seguinte texto para " + targetLanguage + ": " + text)
                .call()
                .content();
    }
    
    public String generateCode(String description, String language) {
        return chatClient.prompt()
                .user("Gere código em " + language + " para: " + description)
                .call()
                .content();
    }
    
    public List<Float> generateEmbedding(String text) {
        // Simulação de embedding - em produção usar OpenAI Embeddings API
        // return openAIEmbeddingClient.embed(text);
        
        // Embedding simulado de 1536 dimensões (tamanho do OpenAI)
        List<Float> embedding = new ArrayList<>();
        Random random = new Random(text.hashCode()); // Seed para consistência
        for (int i = 0; i < 1536; i++) {
            embedding.add(random.nextFloat() * 2 - 1); // Valores entre -1 e 1
        }
        return embedding;
    }
}