package br.com.ucb.book.service;

import br.com.ucb.book.domain.exception.InvalidTokenException;
import br.com.ucb.book.domain.exception.NotFoundException;
import br.com.ucb.book.domain.model.Usuario;
import br.com.ucb.book.domain.persistence.UsuarioPersistence;
import br.com.ucb.book.domain.provider.AuthenticationProvider;
import br.com.ucb.book.domain.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioPersistence usuarioPersistence;

    @Mock
    private AuthenticationProvider authenticationProvider;

    @Test
    void shouldCreateUsuario() {
        Usuario usuario = Usuario
                .builder()
                .email("joao@email.com")
                .senha("senha")
                .documento("70801167167")
                .nome("Joao")
                .build();

        when(usuarioPersistence.criar(any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioRetornado = usuarioService.criar(usuario);
        assertEquals(usuario, usuarioRetornado);
    }

    @Test
    void shouldLogin() {
        Usuario usuario = mock(Usuario.class);

        when(authenticationProvider.autenticar(usuario)).thenReturn("token");

        String token = usuarioService.login(usuario);
        assertEquals("token", token);
    }

    @Test
    void shouldFindUsuarioById() {
        Long userId = 1L;
        Usuario usuario = Usuario
                .builder()
                .nome("Joao")
                .email("joao@email.com")
                .build();

        when(usuarioPersistence.findById(userId)).thenReturn(usuario);

        Usuario usuarioRetornado = usuarioService.findById(userId);

        assertEquals(usuario, usuarioRetornado);
    }

    @Test
    void shouldThrowInvalidTokenExceptionWhenUserNotFound() {
        Long userId = 99L;

        when(usuarioPersistence.findById(userId)).thenThrow(new NotFoundException(""));

        assertThrows(
                InvalidTokenException.class,
                () -> usuarioService.findById(userId)
        );
    }

    @Test
    void shouldConfirmEmail() {
        Long userId = 1L;

        // apenas verifica se o método é chamado sem exceções
        assertDoesNotThrow(() -> usuarioService.confirmaEmail(userId));

        verify(authenticationProvider, times(1)).confirmaEmail(userId);
    }

}
