package br.com.ucb.book.domain.service;

import br.com.ucb.book.domain.model.Usuario;
import br.com.ucb.book.domain.persistence.UsuarioPersistence;
import br.com.ucb.book.domain.provider.AuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioPersistence usuarioPersistence;

    private final AuthenticationProvider authenticationProvider;

    public Usuario criar(Usuario usuario) {
        return usuarioPersistence.criar(usuario);
    }

    public String login(Usuario usuario) {
        return authenticationProvider.autenticar(usuario);
    }
}
