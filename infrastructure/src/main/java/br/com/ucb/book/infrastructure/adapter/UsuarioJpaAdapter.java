package br.com.ucb.book.infrastructure.adapter;

import br.com.ucb.book.domain.exception.ConflictException;
import br.com.ucb.book.domain.model.Usuario;
import br.com.ucb.book.domain.persistence.UsuarioPersistence;
import br.com.ucb.book.infrastructure.entity.UsuarioEntity;
import br.com.ucb.book.infrastructure.mapper.UsuarioEntityMapper;
import br.com.ucb.book.infrastructure.repository.UsuarioRepository;
import br.com.ucb.book.infrastructure.service.EmailService;
import br.com.ucb.book.infrastructure.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioJpaAdapter implements UsuarioPersistence, UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioEntityMapper usuarioEntityMapper;

    private final EmailService emailService;

    private final JwtService jwtService;

    @Override
    @SneakyThrows
    @Transactional
    public Usuario criar(Usuario usuario) {
        usuarioRepository.findByDocumento(usuario.getDocumento()).ifPresent(usuarioEntity -> {
            throw new ConflictException("Já existe usuário com documento '%s'".formatted(usuarioEntity.getDocumento()));
        });
        usuarioRepository.findByUsername(usuario.getEmail()).ifPresent(usuarioEntity -> {
            throw new ConflictException("Já existe usuário com email '%s'".formatted(usuarioEntity.getEmail()));
        }
        );
        UsuarioEntity entity = usuarioEntityMapper.toEntity(usuario);
        UsuarioEntity savedEntity = usuarioRepository.save(entity);
        emailService.enviarConfirmacaoEmail(savedEntity, jwtService.generateTokenForEmail(savedEntity));
        return usuarioEntityMapper.toModel(savedEntity);
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioEntityMapper.toModel(usuarioRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não identificado"))
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
