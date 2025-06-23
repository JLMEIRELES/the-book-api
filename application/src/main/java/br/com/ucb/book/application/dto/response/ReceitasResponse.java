package br.com.ucb.book.application.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReceitasResponse {

    private List<ReceitaData> receitas;
}
