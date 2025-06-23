package br.com.ucb.book.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Usuario {

    private String nome;
    private String documento;
    private String senha;
    private String email;
    private TipoPessoa tipoPessoa;
    private String tipoUsuario;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private String username;
}
