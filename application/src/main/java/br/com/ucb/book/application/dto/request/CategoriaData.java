package br.com.ucb.book.application.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum CategoriaData {

    BEBIDA("Bebida"),
    SOBREMESA("Sobremesa"),
    SALGADO("Salgado"),
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
