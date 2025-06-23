CREATE TABLE IF NOT EXISTS receita (
    id_receita SERIAL PRIMARY KEY NOT NULL,
    id_usuario INTEGER NOT NULL,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    deletado_em TIMESTAMP,
    criado_em TIMESTAMP NOT NULL,
    atualizado_em TIMESTAMP NOT NULL,
    FOREIGN KEY(id_usuario) REFERENCES usuario(id_usuario)
)
