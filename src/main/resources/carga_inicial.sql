-- Carlos Paulino
INSERT INTO usuario (nome, email, login, senha, data_ultima_alteracao, tipo)
SELECT 'Carlos Paulino', 'carlos@email.com', 'carlos', '$2a$10$qXlXrWBnqTnpXKeQxYWG3u73Qg9e58CaJbkd9xC/7p/PDzg9iVfxG', NOW(), 'CLIENTE'
    WHERE NOT EXISTS (
    SELECT 1 FROM usuario WHERE login = 'carlos' OR email = 'carlos@email.com'
);

-- Caike
INSERT INTO usuario (nome, email, login, senha, data_ultima_alteracao, tipo)
SELECT 'Caike', 'caike@email.com', 'caike', '$2a$10$qXlXrWBnqTnpXKeQxYWG3u73Qg9e58CaJbkd9xC/7p/PDzg9iVfxG', NOW(), 'CLIENTE'
    WHERE NOT EXISTS (
    SELECT 1 FROM usuario WHERE login = 'caike' OR email = 'caike@email.com'
);

-- Guilherme
INSERT INTO usuario (nome, email, login, senha, data_ultima_alteracao, tipo)
SELECT 'Guilherme', 'guilher@email.com', 'guilherme', '$2a$10$qXlXrWBnqTnpXKeQxYWG3u73Qg9e58CaJbkd9xC/7p/PDzg9iVfxG', NOW(), 'DONO'
    WHERE NOT EXISTS (
    SELECT 1 FROM usuario WHERE login = 'guilherme' OR email = 'guilher@email.com'
);

-- Jose Vitor
INSERT INTO usuario (nome, email, login, senha, data_ultima_alteracao, tipo)
SELECT 'Jose Vitor', 'jvitor@email.com', 'jvitor', '$2a$10$qXlXrWBnqTnpXKeQxYWG3u73Qg9e58CaJbkd9xC/7p/PDzg9iVfxG', NOW(), 'DONO'
    WHERE NOT EXISTS (
    SELECT 1 FROM usuario WHERE login = 'jvitor' OR email = 'jvitor@email.com'
);

-- Lucas Lopes
INSERT INTO usuario (nome, email, login, senha, data_ultima_alteracao, tipo)
SELECT 'Lucas Lopes', 'lucas@email.com', 'lucas', '$2a$10$qXlXrWBnqTnpXKeQxYWG3u73Qg9e58CaJbkd9xC/7p/PDzg9iVfxG', NOW(), 'CLIENTE'
    WHERE NOT EXISTS (
    SELECT 1 FROM usuario WHERE login = 'lucas' OR email = 'lucas@email.com'
);

-- Endereços: evita duplicação com WHERE NOT EXISTS
INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, cep, usuario_id)
SELECT 'Rua das Palmeiras', '123', 'Apto 301', 'Centro', 'São Paulo', 'SP', '01001-000', 1
WHERE NOT EXISTS (
    SELECT 1 FROM endereco 
    WHERE usuario_id = 1 AND logradouro = 'Rua das Palmeiras' AND numero = '123'
);

INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, cep, usuario_id)
SELECT 'Rua das XPTO', '123', 'Apto 301', 'Centro', 'São Paulo', 'SP', '01001-000', 2
WHERE NOT EXISTS (
    SELECT 1 FROM endereco 
    WHERE usuario_id = 2 AND logradouro = 'Rua das XPTO' AND numero = '123'
);

INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, cep, usuario_id)
SELECT 'Avenida Brasil', '456', 'Casa', 'Jardins', 'Rio de Janeiro', 'RJ', '20000-000', 3
WHERE NOT EXISTS (
    SELECT 1 FROM endereco 
    WHERE usuario_id = 3 AND logradouro = 'Avenida Brasil' AND numero = '456'
);

INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, cep, usuario_id)
SELECT 'Rua Year Zero', '666', 'Apto 666', 'Centro', 'São Paulo', 'SP', '01001-000', 4
WHERE NOT EXISTS (
    SELECT 1 FROM endereco 
    WHERE usuario_id = 4 AND logradouro = 'Rua Year Zero' AND numero = '666'
);

INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, cep, usuario_id)
SELECT 'Rua dos Andradas', '789', 'Bloco B', 'Boa Vista', 'Belo Horizonte', 'MG', '30000-000', 5
WHERE NOT EXISTS (
    SELECT 1 FROM endereco 
    WHERE usuario_id = 5 AND logradouro = 'Rua dos Andradas' AND numero = '789'
);
