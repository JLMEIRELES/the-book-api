package br.com.ucb.book.infrastructure.mapper;

import br.com.ucb.book.domain.model.Receita;
import br.com.ucb.book.infrastructure.entity.ReceitaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReceitaEntityMapper {

    ReceitaEntity toEntity(Receita receita);

    Receita toModel(ReceitaEntity receitaEntity);
}
