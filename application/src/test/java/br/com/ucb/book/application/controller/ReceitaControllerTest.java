package br.com.ucb.book.application.controller;

import br.com.ucb.book.application.dto.CategoriaData;
import br.com.ucb.book.application.dto.request.ReceitaRequest;
import br.com.ucb.book.application.dto.response.ReceitaData;
import br.com.ucb.book.application.dto.response.ReceitasResponse;
import br.com.ucb.book.application.mapper.ReceitaDtoMapper;
import br.com.ucb.book.domain.model.Categoria;
import br.com.ucb.book.domain.model.Receita;
import br.com.ucb.book.domain.service.ReceitaService;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceitaControllerTest {

    @InjectMocks
    private ReceitaController receitaController;

    @Mock
    private ReceitaService receitaService;

    @Spy
    private ReceitaDtoMapper receitaDtoMapper = Mappers.getMapper(ReceitaDtoMapper.class);

    @Mock
    private Authentication authentication;

    @Mock
    private Jwt token;

    @BeforeEach
    void setup() {
        when(authentication.getPrincipal()).thenReturn(token);
        when(token.getClaim("id")).thenReturn(1L);
    }

    @Test
    void shouldCreateReceita() {
        //given
        ReceitaRequest receitaRequest = ReceitaRequest
                .builder()
                .categoria(CategoriaData.BEBIDA)
                .nome("Coca com sorvete")
                .descricao("Mistura coca cola com o sorvete")
                .build();

        //when
        doNothing().when(receitaService).criar(any(Receita.class));

        //then
        assertDoesNotThrow(() -> receitaController.criar(receitaRequest, authentication));
    }

    @Test
    void shouldGetReceitaById() {
        Receita receita = Receita
                .builder()
                .id(1L)
                .categoria(Categoria.BEBIDA)
                .nome("Coca com sorvete")
                .descricao("Mistura coca cola com o sorvete")
                .build();

        when(receitaService.getReceitaById(1L, 1L))
                .thenReturn(receita);

        ReceitaData receitaData = receitaController.getReceitaById(1L, authentication);

        assertAll(
                () -> assertEquals("Coca com sorvete", receitaData.getNome()),
                () -> assertEquals("Mistura coca cola com o sorvete", receitaData.getDescricao()),
                () -> assertEquals(1L, receitaData.getId())
        );
    }

    @Test
    void shouldEditReceita() {
        //given
        ReceitaRequest receitaRequest = ReceitaRequest
                .builder()
                .categoria(CategoriaData.BEBIDA)
                .nome("Coca com sorvete")
                .descricao("Mistura coca cola com o sorvete")
                .build();

        doNothing().when(receitaService).editarReceita(any(Receita.class));

        //when and then
        assertDoesNotThrow(() -> receitaController.editar(1L, receitaRequest, authentication));
    }

    @Test
    void shouldDeleteReceita() {
        //given
        doNothing().when(receitaService).deletar(1L, 1L);

        //when and then
        assertDoesNotThrow(() -> receitaController.deletar(1L, authentication));
    }

    @Test
    void shouldGetReceitasByUser() {
        //given
        Receita receita = Receita
                .builder()
                .id(1L)
                .categoria(Categoria.BEBIDA)
                .nome("Coca com sorvete")
                .descricao("Mistura coca cola com o sorvete")
                .build();
        List<Receita> receitas = List.of(receita);

        when(receitaService.getRecetasByUsuarioId(1L)).thenReturn(receitas);

        //when
        ReceitasResponse response = receitaController.minhasReceitas(authentication);

        //then
        assertAll(
                () -> assertEquals(1, response.getReceitas().size()),
                () -> assertEquals("Coca com sorvete", response.getReceitas().getFirst().getNome())
        );
    }
}
