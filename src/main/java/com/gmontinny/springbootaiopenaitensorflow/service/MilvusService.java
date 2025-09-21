package com.gmontinny.springbootaiopenaitensorflow.service;

import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;
import io.milvus.param.collection.CreateCollectionParam;
import io.milvus.param.collection.FieldType;
import io.milvus.param.dml.InsertParam;
import io.milvus.param.dml.SearchParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class MilvusService {
    
    @Value("${app.milvus.host}")
    private String milvusHost;
    
    @Value("${app.milvus.port}")
    private int milvusPort;
    
    @Value("${app.milvus.collection-name}")
    private String collectionName;
    
    private MilvusServiceClient milvusClient;
    
    @PostConstruct
    public void init() {
        try {
            milvusClient = new MilvusServiceClient(
                ConnectParam.newBuilder()
                    .withHost(milvusHost)
                    .withPort(milvusPort)
                    .build()
            );
            createCollectionIfNotExists();
            log.info("Milvus conectado com sucesso");
        } catch (Exception e) {
            log.warn("Milvus indisponível - funcionando sem busca semântica: {}", e.getMessage());
            milvusClient = null;
        }
    }
    
    private void createCollectionIfNotExists() {
        List<FieldType> fields = Arrays.asList(
            FieldType.newBuilder()
                .withName("id")
                .withDataType(io.milvus.grpc.DataType.Int64)
                .withPrimaryKey(true)
                .withAutoID(true)
                .build(),
            FieldType.newBuilder()
                .withName("message_id")
                .withDataType(io.milvus.grpc.DataType.Int64)
                .build(),
            FieldType.newBuilder()
                .withName("embedding")
                .withDataType(io.milvus.grpc.DataType.FloatVector)
                .withDimension(1536) // OpenAI embedding dimension
                .build()
        );
        
        CreateCollectionParam createParam = CreateCollectionParam.newBuilder()
            .withCollectionName(collectionName)
            .withDescription("Chat message embeddings")
            .withShardsNum(2)
            .withFieldTypes(fields)
            .build();
        
        try {
            milvusClient.createCollection(createParam);
            log.info("Coleção {} criada", collectionName);
        } catch (Exception e) {
            log.debug("Coleção já existe ou erro: {}", e.getMessage());
        }
    }
    
    public void storeEmbedding(Long messageId, List<Float> embedding) {
        if (milvusClient == null) return;
        
        try {
            InsertParam insertParam = InsertParam.newBuilder()
                .withCollectionName(collectionName)
                .withFields(Arrays.asList(
                    new InsertParam.Field("message_id", Arrays.asList(messageId)),
                    new InsertParam.Field("embedding", Arrays.asList(embedding))
                ))
                .build();
            
            milvusClient.insert(insertParam);
            log.debug("Embedding armazenado para mensagem {}", messageId);
        } catch (Exception e) {
            log.error("Erro ao armazenar embedding", e);
        }
    }
    
    public List<Long> searchSimilar(List<Float> queryEmbedding, int topK) {
        if (milvusClient == null) return Arrays.asList();
        
        try {
            SearchParam searchParam = SearchParam.newBuilder()
                .withCollectionName(collectionName)
                .withMetricType(io.milvus.param.MetricType.COSINE)
                .withOutFields(Arrays.asList("message_id"))
                .withTopK(topK)
                .withVectors(Arrays.asList(queryEmbedding))
                .withVectorFieldName("embedding")
                .build();
            
            // Implementar busca e retornar IDs das mensagens similares
            return Arrays.asList(); // Placeholder
        } catch (Exception e) {
            log.error("Erro na busca semântica", e);
            return Arrays.asList();
        }
    }
}