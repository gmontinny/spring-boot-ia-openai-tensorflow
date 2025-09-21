package com.gmontinny.springbootaiopenaitensorflow.controller;

import com.gmontinny.springbootaiopenaitensorflow.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {
    
    private final OpenAIService openAIService;
    
    @PostMapping("/summarize")
    public ResponseEntity<Map<String, String>> summarize(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        String summary = openAIService.summarizeText(text);
        return ResponseEntity.ok(Map.of("summary", summary));
    }
    
    @PostMapping("/translate")
    public ResponseEntity<Map<String, String>> translate(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        String targetLanguage = request.get("targetLanguage");
        String translation = openAIService.translateText(text, targetLanguage);
        return ResponseEntity.ok(Map.of("translation", translation));
    }
    
    @PostMapping("/generate-code")
    public ResponseEntity<Map<String, String>> generateCode(@RequestBody Map<String, String> request) {
        String description = request.get("description");
        String language = request.get("language");
        String code = openAIService.generateCode(description, language);
        return ResponseEntity.ok(Map.of("code", code));
    }
}