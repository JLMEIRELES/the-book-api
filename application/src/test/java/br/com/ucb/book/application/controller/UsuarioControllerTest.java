package br.com.ucb.book.application.controller;

import br.com.ucb.book.application.dto.response.UsuarioResponse;
import br.com.ucb.book.application.mapper.UsuarioDtoMapper;
import br.com.ucb.book.domain.model.Usuario;
import br.com.ucb.book.domain.service.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private Authentication authentication;

    @Spy
    private UsuarioDtoMapper usuarioDtoMapper = Mappers.getMapper(UsuarioDtoMapper.class);

    @Mock
    private Jwt token;

    @BeforeEach
    void setup() {
        when(authentication.getPrincipal()).thenReturn(token);
        when(token.getClaim("id")).thenReturn(1L);
    }

    @Test
    void shouldGetUserName() {
        //given
        Usuario usuario = Usuario
                .builder()
                .nome("Joao")
                .build();
        when(usuarioService.findById(1L)).thenReturn(usuario);

        //when
        UsuarioResponse response = usuarioController.getUsuarioByToken(authentication);

        //then
        assertEquals("Joao", response.getNome());
    }

    @Test
    void sholdConfirmEmail() {
        //given
        doNothing().when(usuarioService).confirmaEmail(1L);

        //when
        Runnable runnable = () -> usuarioController.confirmaEmail(authentication);

        //then
        assertDoesNotThrow(runnable::run);
    }
}
