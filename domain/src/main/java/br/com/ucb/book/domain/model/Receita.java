package br.com.ucb.book.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Receita {

    private Long id;
    private String nome;
    private String descricao;
    private Long usuarioId;
    private Categoria categoria;
}
