package br.com.ucb.book.application.dto.response;

import br.com.ucb.book.application.dto.request.CategoriaData;
import br.com.ucb.book.domain.model.Categoria;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceitaData {

    @JsonProperty("titulo")
    private String nome;

    @JsonProperty("receita")
    private String descricao;

    private CategoriaData categoria;
}
