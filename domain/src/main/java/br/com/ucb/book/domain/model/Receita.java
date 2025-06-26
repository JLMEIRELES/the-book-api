package br.com.ucb.book.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Receita {

    private Long id;
    private String nome;
    private String descricao;
    private Long idUsuario;
    private Categoria categoria;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    public void update(Receita source) {
        this.criadoEm = source.criadoEm;
        this.atualizadoEm = source.atualizadoEm;
    }
}
