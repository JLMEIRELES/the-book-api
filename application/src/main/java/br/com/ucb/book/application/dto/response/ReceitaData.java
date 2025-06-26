package br.com.ucb.book.application.dto.response;

import br.com.ucb.book.application.dto.CategoriaData;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceitaData {

    @Schema(description = "ID da receita", example = "1")
    private Long id;

    @JsonProperty("titulo")
    @Schema(description = "Título da receita", example = "Bolo de cenoura")
    private String nome;

    @JsonProperty("receita")
    @Schema(description = "Modo de preparo ou conteúdo da receita", example = "Misture todos os ingredientes e asse por 40 minutos")
    private String descricao;

    @Schema(description = "Categoria associada à receita")
    private CategoriaData categoria;
}
