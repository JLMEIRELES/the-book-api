package br.com.ucb.book.infrastructure.adapter;

import br.com.ucb.book.domain.exception.ConflictException;
import br.com.ucb.book.domain.messages.Messages;
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

import java.util.Optional;

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
        Optional<UsuarioEntity> usuarioEncontrado = usuarioRepository.findByDocumento(usuario.getDocumento());
        String field = "documento";
        if (usuarioEncontrado.isEmpty()) {
            field = "email";
            usuarioEncontrado = usuarioRepository.findByUsername(usuario.getEmail());
        }
        if (usuarioEncontrado.isPresent()) {
            throw new ConflictException(Messages.USUARIO_EXISTENTE, field);
        }
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
