package br.com.ucb.book.application.mapper;

import br.com.ucb.book.application.dto.request.LoginRequest;
import br.com.ucb.book.application.dto.request.UsuarioRequest;
import br.com.ucb.book.application.dto.response.TokenResponse;
import br.com.ucb.book.application.dto.response.UsuarioResponse;
import br.com.ucb.book.domain.model.TipoPessoa;
import br.com.ucb.book.domain.model.Usuario;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UsuarioDtoMapper {

    @Mapping(target = "tipoPessoa", source = "documento", qualifiedByName = "tipoPessoa")
    Usuario toModel(UsuarioRequest usuarioRequest);

    Usuario toModel(LoginRequest loginRequest);

    @Mapping(target = "accessToken", source = "token")
    TokenResponse toTokenResponse(String token);

    UsuarioResponse toResponse(Usuario usuario);

    @Named("tipoPessoa")
    default TipoPessoa tipoPessoa(String documento) {
        return documento.length() == 11 ? TipoPessoa.PF : TipoPessoa.PJ;
    }

    @BeforeMapping
    default void formataDocumento(UsuarioRequest usuarioRequest) {
        usuarioRequest.setDocumento(usuarioRequest
                        .getDocumento()
                        .replaceAll("\\D", "")
        );
    }
}
