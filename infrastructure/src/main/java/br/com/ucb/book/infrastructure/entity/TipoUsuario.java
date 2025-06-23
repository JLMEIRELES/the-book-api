package br.com.ucb.book.infrastructure.entity;

import org.springframework.security.core.GrantedAuthority;

public enum TipoUsuario implements GrantedAuthority {
    ADMINISTRADOR,
    CLIENTE;

    @Override
    public String getAuthority() {
        return name();
    }
}
