package br.com.ucb.book.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponse {

    @Schema(description = "Nome do usuário", example = "Joao")
    private String nome;
}
