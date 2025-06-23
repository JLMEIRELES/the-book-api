CREATE TABLE IF NOT EXISTS usuario (
    id_usuario SERIAL PRIMARY KEY NOT NULL,
    nome VARCHAR(255) NOT NULL,
    documento VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(50) NOT NULL,
    tipo_pessoa VARCHAR(2) NOT NULL,
    email_confirmado boolean DEFAULT false,
    deletado_em TIMESTAMP,
    criado_em TIMESTAMP NOT NULL,
    atualizado_em TIMESTAMP NOT NULL
)
