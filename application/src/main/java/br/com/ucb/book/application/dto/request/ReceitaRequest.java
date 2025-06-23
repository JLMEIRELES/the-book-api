package br.com.ucb.book.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceitaRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;
}
