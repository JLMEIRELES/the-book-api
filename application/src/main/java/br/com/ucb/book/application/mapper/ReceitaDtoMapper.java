package br.com.ucb.book.application.mapper;

import br.com.ucb.book.application.dto.request.ReceitaRequest;
import br.com.ucb.book.application.dto.response.ReceitaData;
import br.com.ucb.book.application.dto.response.ReceitasResponse;
import br.com.ucb.book.domain.model.Receita;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReceitaDtoMapper {

    Receita toModel(ReceitaRequest receitaRequest, Long usuarioId);

    List<ReceitaData> toResponse(List<Receita> receitas);

    default ReceitasResponse receitasToReceitasResponse(List<Receita> receitas) {
        List<ReceitaData> receitasData = toResponse(receitas);
        return ReceitasResponse.builder()
                .receitas(receitasData)
                .build();
    }
}
