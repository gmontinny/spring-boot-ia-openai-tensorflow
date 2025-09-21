# Spring Boot AI - OpenAI + TensorFlow

Aplicação Spring Boot que integra OpenAI API com TensorFlow para processamento de linguagem natural.

## Funcionalidades

- ✅ Resumir mensagens usando OpenAI GPT
- ✅ Resposta automática inteligente
- ✅ Classificação de sentimento com TensorFlow
- ✅ Geração de descrições de produtos
- ✅ Tradução de textos
- ✅ Geração de código
- ✅ **Busca semântica** com Milvus
- ✅ **Armazenamento vetorial** de embeddings
- ✅ **RAG (Retrieval-Augmented Generation)**

## Arquitetura

```
[Cliente] → [Spring Boot API] → [OpenAI API] (LLM + Embeddings)
                              → [TensorFlow] (ML local)
                              → [H2 Database] (persistência)
                              → [Milvus] (busca vetorial)
                                   ↑
                              [Docker Compose]
                              (etcd + MinIO + Attu)
```

### Camadas da Aplicação

- **Controller**: APIs REST para chat, produtos, IA e busca
- **Service**: Lógica de negócio (OpenAI, TensorFlow, Milvus)
- **Repository**: Persistência JPA com H2
- **Entity**: ChatMessage e Product
- **DTO**: Objetos de transferência validados

## Como Executar

1. Configure a chave da OpenAI no `application.yml`
2. Inicie o Milvus: `docker-compose up -d`
3. Execute: `./mvnw spring-boot:run`
4. Acesse: `http://localhost:8080`
5. Console Milvus (Attu): `http://localhost:8000`

## Endpoints da API

### Chat Inteligente
```bash
# Processar mensagem (resumo + resposta + sentimento)
POST /api/chat/process
{
  "message": "Estou muito feliz com este produto!"
}

# Histórico de mensagens
GET /api/chat/history

# Mensagens por sentimento
GET /api/chat/sentiment/POSITIVE
```

### Produtos
```bash
# Criar produto com descrição gerada por IA
POST /api/products
{
  "name": "Smartphone XYZ",
  "description": "Celular básico",
  "price": 899.99,
  "category": "Eletrônicos"
}

# Listar produtos
GET /api/products
```

### IA Geral
```bash
# Resumir texto
POST /api/ai/summarize
{
  "text": "Texto longo para resumir..."
}

# Traduzir
POST /api/ai/translate
{
  "text": "Hello world",
  "targetLanguage": "português"
}

# Gerar código
POST /api/ai/generate-code
{
  "description": "Função para calcular fibonacci",
  "language": "Java"
}
```

### Busca Semântica (Milvus)
```bash
# Buscar mensagens similares
POST /api/search/semantic
{
  "query": "produto com problema",
  "topK": "5"
}
```

## Fluxo de Processamento Completo

### 1. Processamento de Mensagem
```
Mensagem → OpenAI (resumo + resposta) → TensorFlow (sentimento) → H2 (persistência) → Milvus (embedding)
```

**Exemplo prático:**
1. **Input**: "Adorei este produto, muito bom!"
2. **OpenAI Resume**: "Cliente expressa satisfação com produto"
3. **OpenAI Responde**: "Obrigado pelo feedback positivo!"
4. **TensorFlow Analisa**: POSITIVE (score: 0.85)
5. **H2 Persiste**: Dados estruturados salvos
6. **Milvus Indexa**: Embedding vetorial para busca futura

### 2. Busca Semântica
```
Query → OpenAI (embedding) → Milvus (similaridade) → H2 (dados) → Resultado
```

**Exemplo prático:**
1. **Query**: "problemas com entrega"
2. **Milvus encontra**: Mensagens similares por vetor
3. **Sistema retorna**: Histórico relevante de reclamações

## Stack Tecnológico

### Backend
- **Spring Boot 3.5.6** - Framework principal
- **Spring AI 1.0.2** - Integração com IA
- **Spring Data JPA** - Persistência
- **Lombok** - Redução de boilerplate
- **Validation** - Validação de dados

### Inteligência Artificial
- **OpenAI GPT-3.5-turbo** - LLM para texto
- **OpenAI Embeddings** - Vetorização (simulado)
- **TensorFlow Java 0.5.0** - ML local

### Bancos de Dados
- **H2 Database** - Dados estruturados (memória)
- **Milvus 2.3.3** - Banco vetorial
- **etcd** - Metadados do Milvus
- **MinIO** - Storage do Milvus

### Ferramentas
- **Docker Compose** - Orquestração
- **Attu** - Interface web do Milvus
- **H2 Console** - Interface web do H2

## Interfaces de Administração

### Console H2 (Banco Relacional)
Acesse: `http://localhost:8080/h2-console`
- **URL**: `jdbc:h2:mem:testdb`
- **User**: `sa`
- **Password**: (vazio)

### Attu (Milvus Dashboard)
Acesse: `http://localhost:8000`
- **Visualização**: Coleções e embeddings
- **Gerenciamento**: Índices e consultas
- **Monitoramento**: Performance e estatísticas

## Estrutura do Projeto

```
src/main/java/com/gmontinny/springbootaiopenaitensorflow/
├── config/          # Configurações (ChatClient)
├── controller/      # APIs REST
│   ├── ChatController
│   ├── ProductController
│   ├── AIController
│   └── SearchController
├── dto/             # Transfer Objects
├── entity/          # Entidades JPA
├── repository/      # Repositórios JPA
└── service/         # Lógica de negócio
    ├── OpenAIService
    ├── TensorFlowService
    ├── MilvusService
    ├── ChatService
    └── ProductService
```