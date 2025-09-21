-- Dados de exemplo para teste
INSERT INTO products (name, description, generated_description, price, category, created_at) VALUES
('Notebook Gamer', 'Notebook para jogos', 'Notebook gamer de alta performance com placa de vídeo dedicada', 2999.99, 'Informática', CURRENT_TIMESTAMP);

INSERT INTO products (name, description, generated_description, price, category, created_at) VALUES
('Smartphone Pro', 'Celular avançado', 'Smartphone com câmera profissional e processador de última geração', 1899.99, 'Eletrônicos', CURRENT_TIMESTAMP);

INSERT INTO chat_messages (original_message, summary, auto_response, sentiment, sentiment_score, created_at) VALUES
('Estou muito feliz com minha compra!', 'Cliente expressa satisfação', 'Ficamos felizes em saber! Obrigado pelo feedback.', 'POSITIVE', 0.9, CURRENT_TIMESTAMP);

INSERT INTO chat_messages (original_message, summary, auto_response, sentiment, sentiment_score, created_at) VALUES
('O produto chegou com defeito', 'Cliente reporta problema', 'Lamentamos o ocorrido. Entraremos em contato para resolver.', 'NEGATIVE', 0.2, CURRENT_TIMESTAMP);