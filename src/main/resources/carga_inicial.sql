INSERT IGNORE INTO usuario (nome, email, login, senha, data_ultima_alteracao,
                     endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
                     endereco_cep, tipo)
VALUES
    ('Carlos Paulino', 'carlos@email.com', 'carlos', '$2a$10$qXlXrWBnqTnpXKeQxYWG3u73Qg9e58CaJbkd9xC/7p/PDzg9iVfxG', NOW(),
     'Rua das Flores', '123', 'Apto 1', 'Centro', '01001-000', 'CLIENTE'),

    ('Caike', 'caike@email.com', 'caike', '$2a$10$qXlXrWBnqTnpXKeQxYWG3u73Qg9e58CaJbkd9xC/7p/PDzg9iVfxG', NOW(),
     'Av. Brasil', '456', '', 'Jardins', '01423-000', 'CLIENTE'),

    ('Guilherme', 'guilher@email.com', 'guilherme', '$2a$10$qXlXrWBnqTnpXKeQxYWG3u73Qg9e58CaJbkd9xC/7p/PDzg9iVfxG', NOW(),
     'Rua Augusta', '789', 'Loja', 'Bela Vista', '01305-100', 'DONO'),

    ('Jose Vitor', 'jvitor@email.com', 'jvitor', '$2a$10$qXlXrWBnqTnpXKeQxYWG3u73Qg9e58CaJbkd9xC/7p/PDzg9iVfxG', NOW(),
     'Alameda Santos', '321', 'Casa', 'Paraíso', '01419-000', 'DONO'),

    ('Lucas Lopes', 'lucas@email.com', 'lucas', '$2a$10$qXlXrWBnqTnpXKeQxYWG3u73Qg9e58CaJbkd9xC/7p/PDzg9iVfxG', NOW(),
     'Rua Vergueiro', '654', '', 'Liberdade', '01504-001', 'CLIENTE');
