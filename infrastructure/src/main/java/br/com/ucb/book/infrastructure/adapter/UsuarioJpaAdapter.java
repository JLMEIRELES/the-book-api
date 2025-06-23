package br.com.ucb.book.infrastructure.adapter;

import br.com.ucb.book.domain.exception.ConflictException;
import br.com.ucb.book.domain.model.Usuario;
import br.com.ucb.book.domain.persistence.UsuarioPersistence;
import br.com.ucb.book.infrastructure.entity.UsuarioEntity;
import br.com.ucb.book.infrastructure.mapper.UsuarioEntityMapper;


import br.com.ucb.book.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioJpaAdapter implements UsuarioPersistence, UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioEntityMapper usuarioEntityMapper;

    @Override
    public Usuario criar(Usuario usuario) {
        usuarioRepository.findByDocumento(usuario.getDocumento()).ifPresent(usuarioEntity -> {
            throw new ConflictException("Já existe usuário com documento '%s'".formatted(usuarioEntity.getDocumento()));
        });
        usuarioRepository.findByUsername(usuario.getEmail()).ifPresent(usuarioEntity -> {
            throw new ConflictException("Já existe usuário com email '%s'".formatted(usuarioEntity.getEmail()));
        });
        UsuarioEntity entity = usuarioEntityMapper.toEntity(usuario);
        UsuarioEntity savedEntity = usuarioRepository.save(entity);
        return usuarioEntityMapper.toModel(savedEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
