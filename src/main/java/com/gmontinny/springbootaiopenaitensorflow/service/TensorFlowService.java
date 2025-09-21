package com.gmontinny.springbootaiopenaitensorflow.service;

import com.gmontinny.springbootaiopenaitensorflow.entity.ChatMessage;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TensorFlowService {
    
    public ChatMessage.SentimentType analyzeSentiment(String text) {
        // Simulação de análise de sentimento usando TensorFlow
        // Em produção, você carregaria um modelo real do TensorFlow
        
        double score = calculateSentimentScore(text);
        
        if (score > 0.6) {
            return ChatMessage.SentimentType.POSITIVE;
        } else if (score < 0.4) {
            return ChatMessage.SentimentType.NEGATIVE;
        } else {
            return ChatMessage.SentimentType.NEUTRAL;
        }
    }
    
    public double calculateSentimentScore(String text) {
        // Simulação simples de score de sentimento
        // Em produção, usaria modelo TensorFlow treinado
        
        String lowerText = text.toLowerCase();
        
        String[] positiveWords = {"bom", "ótimo", "excelente", "feliz", "amor", "gosto", "maravilhoso"};
        String[] negativeWords = {"ruim", "péssimo", "ódio", "triste", "problema", "erro", "terrível"};
        
        int positiveCount = 0;
        int negativeCount = 0;
        
        for (String word : positiveWords) {
            if (lowerText.contains(word)) {
                positiveCount++;
            }
        }
        
        for (String word : negativeWords) {
            if (lowerText.contains(word)) {
                negativeCount++;
            }
        }
        
        int totalWords = positiveCount + negativeCount;
        if (totalWords == 0) {
            return 0.5; // Neutro
        }
        
        return (double) positiveCount / totalWords;
    }
    
    // Método para carregar modelo TensorFlow real (exemplo)
    /*
    private void loadTensorFlowModel() {
        try {
            SavedModelBundle model = SavedModelBundle.load(modelPath, "serve");
            log.info("Modelo TensorFlow carregado com sucesso");
        } catch (Exception e) {
            log.error("Erro ao carregar modelo TensorFlow", e);
        }
    }
    */
}