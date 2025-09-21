# Arquitetura Detalhada

## Visão Geral

Esta aplicação implementa uma arquitetura em camadas que integra múltiplas tecnologias de IA:

```
┌─────────────────────────────────────────────────────────────┐
│                        CLIENTE                              │
└─────────────────────┬───────────────────────────────────────┘
                      │ HTTP REST
┌─────────────────────▼───────────────────────────────────────┐
│                 SPRING BOOT API                             │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐           │
│  │   Chat      │ │  Products   │ │   Search    │           │
│  │ Controller  │ │ Controller  │ │ Controller  │           │
│  └─────────────┘ └─────────────┘ └─────────────┘           │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                 SERVICE LAYER                               │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐           │
│  │   OpenAI    │ │ TensorFlow  │ │   Milvus    │           │
│  │  Service    │ │  Service    │ │  Service    │           │
│  └─────────────┘ └─────────────┘ └─────────────┘           │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                 DATA LAYER                                  │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐           │
│  │     H2      │ │   Milvus    │ │   OpenAI    │           │
│  │  Database   │ │  Vector DB  │ │     API     │           │
│  └─────────────┘ └─────────────┘ └─────────────┘           │
└─────────────────────────────────────────────────────────────┘
```

## Componentes Principais

### 1. Controllers (Camada de Apresentação)
- **ChatController**: Processamento de mensagens
- **ProductController**: Gestão de produtos
- **AIController**: Funcionalidades gerais de IA
- **SearchController**: Busca semântica

### 2. Services (Camada de Negócio)
- **OpenAIService**: Integração com GPT e embeddings
- **TensorFlowService**: Análise de sentimento local
- **MilvusService**: Operações vetoriais
- **ChatService**: Orquestração do fluxo principal
- **ProductService**: Lógica de produtos

### 3. Data Layer (Camada de Dados)
- **H2**: Dados estruturados (mensagens, produtos)
- **Milvus**: Embeddings vetoriais
- **OpenAI API**: Modelos de linguagem

## Fluxos de Dados

### Processamento de Mensagem
```
POST /api/chat/process
│
├─ OpenAI: Resumo + Resposta
├─ TensorFlow: Análise de Sentimento  
├─ H2: Persistência
└─ Milvus: Armazenamento Vetorial
```

### Busca Semântica
```
POST /api/search/semantic
│
├─ OpenAI: Gerar embedding da query
├─ Milvus: Buscar vetores similares
└─ H2: Recuperar dados estruturados
```

## Padrões Arquiteturais

- **Dependency Injection**: Spring IoC
- **Repository Pattern**: Spring Data JPA
- **DTO Pattern**: Separação de dados
- **Service Layer**: Lógica de negócio isolada
- **Configuration**: Beans centralizados