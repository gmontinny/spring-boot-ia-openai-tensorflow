package com.gmontinny.springbootaiopenaitensorflow.controller;

import com.gmontinny.springbootaiopenaitensorflow.dto.ChatRequest;
import com.gmontinny.springbootaiopenaitensorflow.dto.ChatResponse;
import com.gmontinny.springbootaiopenaitensorflow.entity.ChatMessage;
import com.gmontinny.springbootaiopenaitensorflow.repository.ChatMessageRepository;
import com.gmontinny.springbootaiopenaitensorflow.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    
    private final ChatService chatService;
    private final ChatMessageRepository chatMessageRepository;
    
    @PostMapping("/process")
    public ResponseEntity<ChatResponse> processMessage(@Valid @RequestBody ChatRequest request) {
        ChatResponse response = chatService.processMessage(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/history")
    public ResponseEntity<List<ChatMessage>> getChatHistory() {
        List<ChatMessage> messages = chatMessageRepository.findByOrderByCreatedAtDesc();
        return ResponseEntity.ok(messages);
    }
    
    @GetMapping("/sentiment/{sentiment}")
    public ResponseEntity<List<ChatMessage>> getMessagesBySentiment(
            @PathVariable ChatMessage.SentimentType sentiment) {
        List<ChatMessage> messages = chatMessageRepository.findBySentiment(sentiment);
        return ResponseEntity.ok(messages);
    }
}