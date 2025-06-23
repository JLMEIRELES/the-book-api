package br.com.ucb.book.infrastructure.mapper;

import br.com.ucb.book.domain.model.Usuario;
import br.com.ucb.book.infrastructure.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = ComponentModel.SPRING)
public abstract class UsuarioEntityMapper {

    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Mapping(target = "senha", expression = "java(passwordEncoder.encode(usuario.getSenha()))")
    public abstract UsuarioEntity toEntity(Usuario usuario);

    public abstract Usuario toModel(UsuarioEntity usuarioEntity);
}
