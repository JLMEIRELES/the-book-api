package br.com.ucb.book.domain.persistence;

import br.com.ucb.book.domain.model.Usuario;

public interface UsuarioPersistence {

    Usuario criar(Usuario usuario);

    Usuario findById(Long id);

}
