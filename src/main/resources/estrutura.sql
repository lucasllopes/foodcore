CREATE TABLE IF NOT EXISTS usuario (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         login VARCHAR(50) NOT NULL UNIQUE,
                         senha VARCHAR(255) NOT NULL,
                         data_ultima_alteracao DATETIME,
                         endereco_logradouro VARCHAR(100),
                         endereco_numero VARCHAR(20),
                         endereco_complemento VARCHAR(50),
                         endereco_bairro VARCHAR(50),
                         endereco_cep VARCHAR(20),
                         tipo VARCHAR(20) NOT NULL
);
