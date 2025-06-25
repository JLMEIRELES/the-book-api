package br.com.ucb.book.application.dto.request;

import br.com.ucb.book.application.dto.CategoriaData;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceitaRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @NotNull(message = "categoria não informada ou inexistente")
    private CategoriaData categoria;
}
