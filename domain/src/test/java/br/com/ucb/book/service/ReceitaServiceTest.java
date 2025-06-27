package br.com.ucb.book.service;

import br.com.ucb.book.domain.model.Categoria;
import br.com.ucb.book.domain.model.Receita;
import br.com.ucb.book.domain.persistence.ReceitaPersistence;
import br.com.ucb.book.domain.service.ReceitaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceitaServiceTest {

    @InjectMocks
    private ReceitaService receitaService;

    @Mock
    private ReceitaPersistence receitaPersistence;

    @Test
    void shouldGetReceitaById() {
        //given
        Receita receita = Receita
                .builder()
                .id(1L)
                .categoria(Categoria.BEBIDA)
                .nome("Coca com sorvete")
                .descricao("Mistura coca cola com o sorvete")
                .build();

        Long usuarioId = 1L;
        Long receitaId = 1L;

        when(receitaPersistence.getReceitaById(usuarioId, receitaId)).thenReturn(receita);

        //when
        Receita receitaRetornada = receitaService.getReceitaById(1L, 1L);

        //then
        assertEquals(receita, receitaRetornada);
    }

    @Test
    void shouldGetReceitasByUsuarioId() {
        //given
        Receita receita = Receita
                .builder()
                .id(1L)
                .categoria(Categoria.BEBIDA)
                .nome("Coca com sorvete")
                .descricao("Mistura coca cola com o sorvete")
                .build();

        Long usuarioId = 1L;

        when(receitaPersistence.getByUsuarioId(usuarioId)).thenReturn(List.of(receita));

        //when
        List<Receita> receitas = receitaService.getRecetasByUsuarioId(1L);

        //then
        assertAll(
                () -> assertEquals(1, receitas.size()),
                () -> assertEquals(receita, receitas.getFirst())
        );
    }

    @Test
    void shouldEditReceita() {
        //given
        Receita receita = Receita
                .builder()
                .id(1L)
                .categoria(Categoria.BEBIDA)
                .nome("Coca com sorvete")
                .descricao("Mistura coca cola com o sorvete")
                .criadoEm(LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .build();

        Receita receitaParaEditar = Receita
                .builder()
                .id(1L)
                .idUsuario(1L)
                .categoria(Categoria.BEBIDA)
                .nome("Coca com sorvete 2")
                .descricao("Mistura coca cola com o sorvete")
                .build();

        Long receitaId = 1L;
        Long usuarioId = 1L;

        when(receitaService.getReceitaById(usuarioId, receitaId)).thenReturn(receita);
        doNothing().when(receitaPersistence).salvar(receitaParaEditar);

        //when
        Runnable runnable = () -> receitaService.editarReceita(receitaParaEditar);

        //then
        assertAll(
                () -> assertDoesNotThrow(runnable::run),
                () -> assertEquals(LocalDateTime.now().getDayOfYear(), receitaParaEditar.getCriadoEm().getDayOfYear())
        );
    }

    @Test
    void shouldDeleteReceita() {
        Long receitaId = 1L;
        Long usuarioId = 1L;

        Receita receita = Receita
                .builder()
                .id(1L)
                .categoria(Categoria.BEBIDA)
                .nome("Coca com sorvete")
                .descricao("Mistura coca cola com o sorvete")
                .criadoEm(LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .build();
        when(receitaService.getReceitaById(usuarioId, receitaId)).thenReturn(receita);
        doNothing().when(receitaPersistence).deletar(receita);

        //when
        Runnable runnable = () -> receitaService.deletar(receitaId, usuarioId);

        //then
        assertDoesNotThrow(runnable::run);
    }

    @Test
    void shouldCreateReceita() {
        Receita receita = Receita
                .builder()
                .id(1L)
                .categoria(Categoria.BEBIDA)
                .nome("Coca com sorvete")
                .descricao("Mistura coca cola com o sorvete")
                .criadoEm(LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .build();

        doNothing().when(receitaPersistence).salvar(receita);

        Runnable runnable = () -> receitaService.criar(receita);
        assertDoesNotThrow(runnable::run);
    }
}
