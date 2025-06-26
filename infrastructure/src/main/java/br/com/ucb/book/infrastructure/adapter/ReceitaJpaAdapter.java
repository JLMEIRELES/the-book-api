package br.com.ucb.book.infrastructure.adapter;

import br.com.ucb.book.domain.exception.NotFoundException;
import br.com.ucb.book.domain.exception.UnauthorizedException;
import br.com.ucb.book.domain.messages.Messages;
import br.com.ucb.book.domain.model.Receita;
import br.com.ucb.book.domain.persistence.ReceitaPersistence;
import br.com.ucb.book.infrastructure.entity.ReceitaEntity;
import br.com.ucb.book.infrastructure.entity.UsuarioEntity;
import br.com.ucb.book.infrastructure.mapper.ReceitaEntityMapper;
import br.com.ucb.book.infrastructure.repository.ReceitaRepository;
import br.com.ucb.book.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReceitaJpaAdapter implements ReceitaPersistence {

    private final ReceitaRepository receitaRepository;

    private final UsuarioRepository usuarioRepository;

    private final ReceitaEntityMapper receitaEntityMapper;

    @Transactional
    @Override
    public void salvar(Receita receita) {
        Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findById(receita.getIdUsuario());
        if (usuarioOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuário logado não existe");
        }
        UsuarioEntity usuario = usuarioOptional.get();
        ReceitaEntity receitaEntity = receitaEntityMapper.toEntity(receita);
        receitaEntity.setUsuario(usuario);
        receitaRepository.save(receitaEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Receita> getByUsuarioId(Long usuarioId) {
        return receitaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(receitaEntityMapper::toModel)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Receita getReceitaById(Long usuarioId, Long receitaId) {
        ReceitaEntity receitaEntity = usuarioRepository.findByReceitaId(receitaId)
                .map(UsuarioEntity::getReceitas)
                .flatMap(receitas -> receitas.stream().findFirst())
                .orElseThrow(() -> new NotFoundException(Messages.RECEITA_NAO_ENCONTRADA.formatted(receitaId)));

        if (!receitaEntity.getUsuario().getId().equals(usuarioId)) {
            throw new UnauthorizedException();
        }

        return receitaEntityMapper.toModel(receitaEntity);
    }
}
