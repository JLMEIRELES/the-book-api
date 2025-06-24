package br.com.ucb.book.domain.provider;

import br.com.ucb.book.domain.model.Usuario;

public interface AuthenticationProvider {

    String autenticar(Usuario usuario);

    void confirmaEmail(Long idUsuario);
}
