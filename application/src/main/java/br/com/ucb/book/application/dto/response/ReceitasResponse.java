package br.com.ucb.book.application.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReceitasResponse {

    @ArraySchema(
            arraySchema = @Schema(description = "Lista de receitas"),
            schema = @Schema(implementation = ReceitaData.class)
    )
    private List<ReceitaData> receitas;
}
