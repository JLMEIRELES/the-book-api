package br.com.ucb.book.application.dto.request;

import br.com.ucb.book.application.dto.CategoriaData;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceitaRequest {

    @NotBlank
    @Schema(description = "Nome da receita", example = "Bolo de cenoura", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome;

    @NotBlank
    @Schema(description = "Descrição detalhada do modo de preparo", example = "Misture os ingredientes e asse por 40 minutos",  requiredMode = Schema.RequiredMode.REQUIRED)
    private String descricao;

    @NotNull(message = "categoria não informada ou inexistente")
    @Schema(description = "Categoria da receita", requiredMode = Schema.RequiredMode.REQUIRED)
    private CategoriaData categoria;
}
