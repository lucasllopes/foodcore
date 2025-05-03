INSERT IGNORE INTO usuario (nome, email, login, senha, data_ultima_alteracao, tipo)
VALUES
    ('Carlos Paulino', 'carlos@email.com', 'carlos', '$2a$10$qXlXrWBnqTnpXKeQxYWG3u73Qg9e58CaJbkd9xC/7p/PDzg9iVfxG', NOW(), 'CLIENTE'),
    ('Caike', 'caike@email.com', 'caike', '$2a$10$qXlXrWBnqTnpXKeQxYWG3u73Qg9e58CaJbkd9xC/7p/PDzg9iVfxG', NOW(), 'CLIENTE'),
    ('Guilherme', 'guilher@email.com', 'guilherme', '$2a$10$qXlXrWBnqTnpXKeQxYWG3u73Qg9e58CaJbkd9xC/7p/PDzg9iVfxG', NOW(), 'DONO'),
    ('Jose Vitor', 'jvitor@email.com', 'jvitor', '$2a$10$qXlXrWBnqTnpXKeQxYWG3u73Qg9e58CaJbkd9xC/7p/PDzg9iVfxG', NOW(), 'DONO'),
    ('Lucas Lopes', 'lucas@email.com', 'lucas', '$2a$10$qXlXrWBnqTnpXKeQxYWG3u73Qg9e58CaJbkd9xC/7p/PDzg9iVfxG', NOW(), 'CLIENTE');


    INSERT IGNORE INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, cep, usuario_id)
VALUES
    ('Rua das Palmeiras', '123', 'Apto 301', 'Centro', 'São Paulo', 'SP', '01001-000', 1),
    ('Rua das XPTO', '123', 'Apto 301', 'Centro', 'São Paulo', 'SP', '01001-000', 2),
    ('Avenida Brasil', '456', 'Casa', 'Jardins', 'Rio de Janeiro', 'RJ', '20000-000', 3),
    ('Rua Year Zero', '666', 'Apto 666', 'Centro', 'São Paulo', 'SP', '01001-000', 4),
    ('Rua dos Andradas', '789', 'Bloco B', 'Boa Vista', 'Belo Horizonte', 'MG', '30000-000', 5);
