package br.com.ucb.book.application.controller;

import br.com.ucb.book.application.dto.request.LoginRequest;
import br.com.ucb.book.application.dto.request.UsuarioRequest;
import br.com.ucb.book.application.dto.response.TokenResponse;
import br.com.ucb.book.application.mapper.UsuarioDtoMapper;
import br.com.ucb.book.domain.model.Usuario;
import br.com.ucb.book.domain.service.UsuarioService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @InjectMocks
    private AutenticacaoController autenticacaoController;

    @Mock
    private UsuarioService usuarioService;

    @Spy
    private UsuarioDtoMapper usuarioDtoMapper = Mappers.getMapper(UsuarioDtoMapper.class);

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldValidateUserRequest() {
        //given
        UsuarioRequest usuarioRequest = UsuarioRequest
                .builder()
                .email("joao#email.com")
                .senha("senha")
                .confirmaSenha("senhaNaoConfirma")
                .documento("teste-erro")
                .nome("Joao")
                .build();

        //when
        var violations = validator.validate(usuarioRequest);
        var documentoViolation = violations
                .stream()
                .filter(v -> v.getPropertyPath().toString().equals("documento"))
                .toList();
        var emailViolation =  violations
                .stream()
                .filter(v -> v.getPropertyPath().toString().equals("email"))
                .toList();
        var senhaViolation =  violations
                .stream()
                .filter(v -> v.getPropertyPath().toString().equals("senha"))
                .toList();

        //then
        assertAll(
                () -> assertEquals(3, violations.size()),
                () -> assertEquals(1, documentoViolation.size()),
                () -> assertEquals(1, emailViolation.size()),
                () -> assertEquals(1, senhaViolation.size())
        );
    }

    @Test
    void shouldCreateUser() {
        //given
        UsuarioRequest usuarioRequest = UsuarioRequest
                .builder()
                .email("joao@email.com")
                .senha("senha")
                .confirmaSenha("senha")
                .documento("70801167167")
                .nome("Joao")
                .build();
        Usuario usuario = usuarioDtoMapper.toModel(usuarioRequest);
        when(usuarioService.criar(any(Usuario.class))).thenReturn(usuario);

        //when
        var violations = validator.validate(usuarioRequest);
        Runnable runnable = () -> autenticacaoController.cadastrar(usuarioRequest);

        //then
        assertAll(
                () -> assertDoesNotThrow(runnable::run),
                () -> assertEquals(0, violations.size()),
                () -> verify(usuarioService, times(1)).criar(usuario)
        );
    }

    @Test
    void shouldNotLogin() {
        //given
        LoginRequest login = LoginRequest
                .builder()
                .username("username")
                .senha("senha")
                .build();
        when(usuarioService.login(any(Usuario.class)))
                .thenThrow(BadCredentialsException.class);

        //when and then
        assertThrows(
                BadCredentialsException.class,
                () -> autenticacaoController.login(login)
        );
    }

    @Test
    void shouldLogin() {
        //given
        LoginRequest login = LoginRequest
                .builder()
                .username("username")
                .senha("senha")
                .build();
        when(usuarioService.login(any(Usuario.class)))
                .thenReturn("token");

        //when
        TokenResponse token = autenticacaoController.login(login);

        //then
        assertEquals("token", token.getAccessToken());
    }
}
