package com.gmontinny.springbootaiopenaitensorflow.dto;

import com.gmontinny.springbootaiopenaitensorflow.entity.ChatMessage;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class ChatResponse {
    private String summary;
    private String autoResponse;
    private ChatMessage.SentimentType sentiment;
    private Double sentimentScore;
}