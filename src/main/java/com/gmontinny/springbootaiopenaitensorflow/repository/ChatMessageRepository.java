package com.gmontinny.springbootaiopenaitensorflow.repository;

import com.gmontinny.springbootaiopenaitensorflow.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findBySentiment(ChatMessage.SentimentType sentiment);
    List<ChatMessage> findByOrderByCreatedAtDesc();
}