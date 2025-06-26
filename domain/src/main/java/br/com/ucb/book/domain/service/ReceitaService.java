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

    public void criar(Receita receita) {
        receitaPersistence.salvar(receita);
    }

    public List<Receita> getRecetasByUsuarioId(Long usuarioId) {
        return receitaPersistence.getByUsuarioId(usuarioId);
    }

    public Receita getReceitaById(Long userId, Long receitaId) {
        return receitaPersistence.getReceitaById(userId, receitaId);
    }

    public void editarReceita(Receita receita) {
        Receita receitaEncontrada = getReceitaById(receita.getIdUsuario(), receita.getId());
        receita.update(receitaEncontrada);
        receitaPersistence.salvar(receita);
    }
}
