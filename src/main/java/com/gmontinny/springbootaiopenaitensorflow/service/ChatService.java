package com.gmontinny.springbootaiopenaitensorflow.service;

import com.gmontinny.springbootaiopenaitensorflow.dto.ChatRequest;
import com.gmontinny.springbootaiopenaitensorflow.dto.ChatResponse;
import com.gmontinny.springbootaiopenaitensorflow.entity.ChatMessage;
import com.gmontinny.springbootaiopenaitensorflow.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    
    private final OpenAIService openAIService;
    private final TensorFlowService tensorFlowService;
    private final ChatMessageRepository chatMessageRepository;
    private final MilvusService milvusService;
    
    @Transactional
    public ChatResponse processMessage(ChatRequest request) {
        log.info("Processando mensagem: {}", request.getMessage());
        
        // 1. Resumir usando OpenAI
        String summary = openAIService.summarizeText(request.getMessage());
        
        // 2. Gerar resposta automática usando OpenAI
        String autoResponse = openAIService.generateAutoResponse(request.getMessage());
        
        // 3. Analisar sentimento usando TensorFlow
        ChatMessage.SentimentType sentiment = tensorFlowService.analyzeSentiment(request.getMessage());
        double sentimentScore = tensorFlowService.calculateSentimentScore(request.getMessage());
        
        // 4. Salvar no banco de dados
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setOriginalMessage(request.getMessage());
        chatMessage.setSummary(summary);
        chatMessage.setAutoResponse(autoResponse);
        chatMessage.setSentiment(sentiment);
        chatMessage.setSentimentScore(sentimentScore);
        
        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);
        
        // 5. Gerar e armazenar embedding no Milvus para busca semântica
        try {
            List<Float> embedding = openAIService.generateEmbedding(request.getMessage());
            milvusService.storeEmbedding(savedMessage.getId(), embedding);
        } catch (Exception e) {
            log.warn("Falha ao armazenar embedding: {}", e.getMessage());
        }
        
        log.info("Mensagem processada e salva com ID: {}", savedMessage.getId());
        
        return new ChatResponse(summary, autoResponse, sentiment, sentimentScore);
    }
}