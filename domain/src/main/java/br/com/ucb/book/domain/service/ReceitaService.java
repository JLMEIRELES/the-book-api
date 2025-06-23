package br.com.ucb.book.domain.service;

import br.com.ucb.book.domain.model.Receita;
import br.com.ucb.book.domain.persistence.ReceitaPersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceitaService {

    private final ReceitaPersistence receitaPersistence;

    public Receita criar(Receita receita) {
        return receitaPersistence.criar(receita);
    }

    public List<Receita> getRecetasByUsuarioId(Long usuarioId) {
        return receitaPersistence.getByUsuarioId(usuarioId);
    }
}
