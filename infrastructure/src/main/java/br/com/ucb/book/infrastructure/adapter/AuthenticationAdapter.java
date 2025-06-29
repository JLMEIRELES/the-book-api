package br.com.ucb.book.infrastructure.adapter;

import br.com.ucb.book.domain.model.Usuario;
import br.com.ucb.book.domain.provider.AuthenticationProvider;
import br.com.ucb.book.infrastructure.entity.UsuarioEntity;
import br.com.ucb.book.infrastructure.repository.UsuarioRepository;
import br.com.ucb.book.infrastructure.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthenticationAdapter implements AuthenticationProvider {

    private final UsuarioRepository usuarioRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public String autenticar(Usuario usuario) throws BadCredentialsException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getSenha())
        );
        UsuarioEntity usuarioEntity = (UsuarioEntity) authentication.getPrincipal();
        return jwtService.generateJwt(usuarioEntity);
    }

    @Override
    @Transactional
    public void confirmaEmail(Long idUsuario) {
        int usuariosConfirmados = usuarioRepository.confirmarEmail(idUsuario);
        if (usuariosConfirmados > 1) {
            throw new IllegalArgumentException("Houve um erro ao confirmar e-mail do usuário");
        }
    }
}
