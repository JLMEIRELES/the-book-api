package br.com.ucb.book.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum CategoriaData {

    @Schema(description = "Bebidas como sucos, refrigerantes, etc.")
    BEBIDA("Bebida"),

    @Schema(description = "Doces e sobremesas")
    SOBREMESA("Sobremesa"),

    @Schema(description = "Salgados como tortas, pães, etc.")
    SALGADO("Salgado"),

    @Schema(description = "Receitas saudáveis e fitness")
    FITNESS("Fitness");

    private final String descricao;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static CategoriaData from(String descricao) {
        return Arrays.stream(CategoriaData.values())
                .filter(c -> c.getDescricao().equalsIgnoreCase(descricao))
                .findFirst()
                .orElse(null);
    }

    @JsonValue
    public String getDescricao() {
        return this.descricao;
    }
}
