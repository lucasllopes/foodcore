CREATE TABLE IF NOT EXISTS tipo_usuario (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100),
    last_modified TIMESTAMP
);

CREATE TABLE IF NOT EXISTS usuario (
                                       id BIGSERIAL PRIMARY KEY,
                                       nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    data_ultima_alteracao TIMESTAMP,
    tipo VARCHAR(20) NOT NULL,
    tipo_usuario_id BIGINT,
    CONSTRAINT fk_tipo_usuario FOREIGN KEY (tipo_usuario_id) REFERENCES tipo_usuario(id)
    );

CREATE TABLE IF NOT EXISTS endereco (
                                        id BIGSERIAL PRIMARY KEY,
                                        logradouro VARCHAR(100),
    numero VARCHAR(20),
    complemento VARCHAR(50),
    bairro VARCHAR(50),
    cidade VARCHAR(50),
    estado VARCHAR(50),
    cep VARCHAR(20),
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_endereco_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS endereco_restaurante (
                                                    id BIGSERIAL PRIMARY KEY,
                                                    logradouro VARCHAR(100),
    numero VARCHAR(20),
    complemento VARCHAR(50),
    bairro VARCHAR(50),
    cidade VARCHAR(50),
    estado VARCHAR(50),
    cep VARCHAR(20)
    );

CREATE TABLE IF NOT EXISTS restaurante (
                                           id BIGSERIAL PRIMARY KEY,
                                           name VARCHAR(100),
    cuisine_type VARCHAR(50),
    opening_hours TIME,
    closing_hours TIME,
    dono_id BIGINT NOT NULL,
    address_id BIGINT NOT NULL,

    CONSTRAINT fk_restaurante_dono FOREIGN KEY (dono_id) REFERENCES usuario(id) ON DELETE CASCADE,
    CONSTRAINT fk_restaurante_address FOREIGN KEY (address_id) REFERENCES endereco_restaurante(id) ON DELETE CASCADE
    );





-- CREATE TABLE IF NOT EXISTS restaurante (
--                                            id BIGSERIAL PRIMARY KEY,
--                                            name VARCHAR(100),
--     cuisineType VARCHAR(50),
--     openingHours TIME,
--     closingHours TIME,
--     dono_id BIGINT NOT NULL,
--     CONSTRAINT fk_restaurante_dono FOREIGN KEY (dono_id) REFERENCES usuario(id) ON DELETE CASCADE
--     );


CREATE TABLE IF NOT EXISTS item (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    price NUMERIC(12,2) NOT NULL,
    availability VARCHAR(50),
    photo VARCHAR(255),
    dono_id BIGINT NOT NULL,
    CONSTRAINT fk_restaurante_dono FOREIGN KEY (dono_id) REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS menu (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    restaurant_id BIGINT NOT NULL,
    CONSTRAINT fk_menu_restaurante FOREIGN KEY (restaurant_id) REFERENCES restaurante(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS menu_item (
    menu_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    PRIMARY KEY (menu_id, item_id),
    FOREIGN KEY (menu_id) REFERENCES menu(id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES item(id) ON DELETE CASCADE
);




