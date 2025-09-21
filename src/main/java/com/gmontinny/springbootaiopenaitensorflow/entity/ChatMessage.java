package com.gmontinny.springbootaiopenaitensorflow.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(columnDefinition = "TEXT")
    private String originalMessage;
    
    @Column(columnDefinition = "TEXT")
    private String summary;
    
    @Column(columnDefinition = "TEXT")
    private String autoResponse;
    
    @Enumerated(EnumType.STRING)
    private SentimentType sentiment;
    
    private Double sentimentScore;
    
    private LocalDateTime createdAt = LocalDateTime.now();
    
    public enum SentimentType {
        POSITIVE, NEGATIVE, NEUTRAL
    }
}