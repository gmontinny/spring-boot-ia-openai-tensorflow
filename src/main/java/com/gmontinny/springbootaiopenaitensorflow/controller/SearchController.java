package com.gmontinny.springbootaiopenaitensorflow.controller;

import com.gmontinny.springbootaiopenaitensorflow.entity.ChatMessage;
import com.gmontinny.springbootaiopenaitensorflow.repository.ChatMessageRepository;
import com.gmontinny.springbootaiopenaitensorflow.service.MilvusService;
import com.gmontinny.springbootaiopenaitensorflow.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {
    
    private final MilvusService milvusService;
    private final OpenAIService openAIService;
    private final ChatMessageRepository chatMessageRepository;
    
    @PostMapping("/semantic")
    public ResponseEntity<List<ChatMessage>> semanticSearch(@RequestBody Map<String, String> request) {
        String query = request.get("query");
        int topK = Integer.parseInt(request.getOrDefault("topK", "5"));
        
        // Gerar embedding da consulta
        List<Float> queryEmbedding = openAIService.generateEmbedding(query);
        
        // Buscar mensagens similares no Milvus
        List<Long> similarIds = milvusService.searchSimilar(queryEmbedding, topK);
        
        // Recuperar mensagens do banco
        List<ChatMessage> similarMessages = similarIds.stream()
            .map(id -> chatMessageRepository.findById(id).orElse(null))
            .filter(msg -> msg != null)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(similarMessages);
    }
}