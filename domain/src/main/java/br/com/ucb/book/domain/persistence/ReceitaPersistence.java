package br.com.ucb.book.domain.persistence;

import br.com.ucb.book.domain.model.Receita;

import java.util.List;

public interface ReceitaPersistence {

    void salvar(Receita receita);

    List<Receita> getByUsuarioId(Long usuarioId);

    Receita getReceitaById(Long usuarioId, Long receitaId);

    void deletar(Receita receita);
}
