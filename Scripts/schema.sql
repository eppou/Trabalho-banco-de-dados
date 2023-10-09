CREATE SCHEMA crime_db;

CREATE SEQUENCE crime_db.id_crime_seq
    START 1 INCREMENT 1;

CREATE TABLE crime_db.crimes (
    id_crime INT DEFAULT nextval('crime_db.id_crime_seq'),
    tipo VARCHAR(255) NOT NULL,
    descricao TEXT,
    data_crime DATE,
    latitude DECIMAL(9, 6),
    longitude DECIMAL(9, 6),
    nome_cidade VARCHAR(255) NOT NULL,
    nome_estado VARCHAR(255) NOT NULL,
    sigla_estado VARCHAR(2) NOT NULL,
	nome_pais VARCHAR(255) NOT NULL,
    CONSTRAINT pk_crimes PRIMARY KEY(id_crime)
);

CREATE TABLE crime_db.outros_crimes (
    id_crime INT,
    CONSTRAINT pk_outros_crimes PRIMARY KEY (id_crime),
    CONSTRAINT fk_outros_crimes FOREIGN KEY (id_crime) REFERENCES crime_db.crimes(id_crime)
    ON DELETE CASCADE
);

CREATE TABLE crime_db.crimes_brutais (
    id_crime INT,
    num_vitimas INT DEFAULT -1,
    CONSTRAINT pk_crimes_brutais PRIMARY KEY (id_crime),
    CONSTRAINT fk_crimes_brutais FOREIGN KEY (id_crime) REFERENCES crime_db.crimes(id_crime)
    ON DELETE CASCADE
);


CREATE TABLE crime_db.pais (
    nome VARCHAR(255),
    area DECIMAL(10, 2),
    populacao INT,
    idh DECIMAL(5, 3),
    rpc_pais DECIMAL(10, 2),
    CONSTRAINT pk_pais PRIMARY KEY (nome),
    CONSTRAINT ck_pais_area CHECK(area > 0),
    CONSTRAINT ck_pais_populacao CHECK(populacao > 0),
    CONSTRAINT ck_pais_idh CHECK(idh > 0 AND idh < 1),  
    CONSTRAINT ck_pais_rpc CHECK(rpc_pais > 0)

);

CREATE TABLE crime_db.estado (
    sigla VARCHAR(2),
    nome VARCHAR(255),
    nome_pais VARCHAR(255),
    rpc_estado DECIMAL(10, 2),
    area_estado DECIMAL(10, 2),
    populacao_estado INT,
    CONSTRAINT pk_estado PRIMARY KEY (sigla,nome,nome_pais),
    CONSTRAINT fk_estado FOREIGN KEY (nome_pais) REFERENCES crime_db.pais(nome),
    CONSTRAINT ck_estado_area CHECK(area_estado > 0),
    CONSTRAINT ck_estado_populacao CHECK(populacao_estado > 0), 
    CONSTRAINT ck_estado_rpc CHECK(rpc_estado > 0)

);

CREATE TABLE crime_db.cidade (
    nome VARCHAR(255),
    nome_estado VARCHAR(255),
    sigla_estado VARCHAR(2),
    nome_pais VARCHAR(255), 
    area_cidade DECIMAL(10, 2),
    populacao_cidade INT,
    rpc_cidade DECIMAL(10, 2),
    CONSTRAINT pk_cidade PRIMARY KEY (nome, nome_estado, sigla_estado, nome_pais),
    CONSTRAINT fk_cidade FOREIGN KEY (sigla_estado, nome_estado, nome_pais) 
    REFERENCES crime_db.estado(sigla, nome, nome_pais), 
    CONSTRAINT ck_cidade_area CHECK(area_cidade > 0),
    CONSTRAINT ck_cidade_populacao CHECK(populacao_cidade > 0), 
    CONSTRAINT ck_cidade_rpc CHECK(rpc_cidade > 0)
);

ALTER TABLE crime_db.crimes
	ADD CONSTRAINT fk_crimes FOREIGN KEY (nome_cidade,nome_estado,sigla_estado,nome_pais) 
    REFERENCES crime_db.cidade(nome,nome_estado,sigla_estado, nome_pais);