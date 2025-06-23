package br.com.ucb.book.infrastructure.adapter;

import br.com.ucb.book.domain.model.Receita;
import br.com.ucb.book.domain.persistence.ReceitaPersistence;
import br.com.ucb.book.infrastructure.entity.ReceitaEntity;
import br.com.ucb.book.infrastructure.entity.UsuarioEntity;
import br.com.ucb.book.infrastructure.mapper.ReceitaEntityMapper;
import br.com.ucb.book.infrastructure.repository.ReceitaRepository;
import br.com.ucb.book.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReceitaJpaAdapter implements ReceitaPersistence {

    private final ReceitaRepository receitaRepository;

    private final UsuarioRepository usuarioRepository;

    private final ReceitaEntityMapper receitaEntityMapper;

    @Override
    public Receita criar(Receita receita) {
        Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findById(receita.getUsuarioId());
        if (usuarioOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuário logado não existe");
        }
        UsuarioEntity usuario = usuarioOptional.get();
        ReceitaEntity receitaEntity = receitaEntityMapper.toEntity(receita);
        receitaEntity.setUsuario(usuario);
        return receitaEntityMapper.toModel(receitaRepository.save(receitaEntity));
    }

    @Override
    public List<Receita> getByUsuarioId(Long usuarioId) {
        return receitaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(receitaEntityMapper::toModel)
                .toList();
    }
}
