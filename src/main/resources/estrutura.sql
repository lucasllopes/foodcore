CREATE TABLE IF NOT EXISTS usuario (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         login VARCHAR(50) NOT NULL UNIQUE,
                         senha VARCHAR(255) NOT NULL,
                         data_ultima_alteracao DATETIME,
                         logradouro VARCHAR(100),
                         numero VARCHAR(20),
                         complemento VARCHAR(50),
                         bairro VARCHAR(50),
                         cep VARCHAR(20),
                         estado VARCHAR(20),
                         cidade VARCHAR(20),
                         tipo VARCHAR(20) NOT NULL
);
