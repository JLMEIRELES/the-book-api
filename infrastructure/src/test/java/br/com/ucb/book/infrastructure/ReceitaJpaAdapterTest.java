package br.com.ucb.book.infrastructure;

import br.com.ucb.book.domain.exception.NotFoundException;
import br.com.ucb.book.domain.exception.UnauthorizedException;
import br.com.ucb.book.domain.model.Categoria;
import br.com.ucb.book.domain.model.Receita;
import br.com.ucb.book.infrastructure.adapter.ReceitaJpaAdapter;
import br.com.ucb.book.infrastructure.entity.ReceitaEntity;
import br.com.ucb.book.infrastructure.entity.UsuarioEntity;
import br.com.ucb.book.infrastructure.mapper.ReceitaEntityMapper;
import br.com.ucb.book.infrastructure.repository.ReceitaRepository;
import br.com.ucb.book.infrastructure.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReceitaJpaAdapterTest {

    @InjectMocks
    private ReceitaJpaAdapter receitaJpaAdapter;

    @Mock
    private ReceitaRepository receitaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Spy
    private ReceitaEntityMapper receitaEntityMapper = Mappers.getMapper(ReceitaEntityMapper.class);

    @Test
    void shouldSalvarReceitaComUsuarioExistente() {
        Receita receita = Receita.builder()
                .id(1L)
                .nome("Bolo")
                .descricao("Receita de bolo")
                .idUsuario(10L)
                .categoria(Categoria.SALGADO)
                .build();

        UsuarioEntity usuarioEntity = UsuarioEntity.builder()
                .id(10L)
                .build();

        when(usuarioRepository.findById(10L)).thenReturn(Optional.of(usuarioEntity));

        assertDoesNotThrow(() -> receitaJpaAdapter.salvar(receita));

        verify(receitaRepository, times(1)).save(any(ReceitaEntity.class));
    }

    @Test
    void shouldThrowWhenUsuarioNaoExisteAoSalvar() {
        Receita receita = Receita.builder()
                .idUsuario(999L)
                .nome("Bolo")
                .build();

        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> receitaJpaAdapter.salvar(receita));

        assertEquals("Usuário logado não existe", exception.getMessage());
        verify(receitaRepository, never()).save(any());
    }

    @Test
    void shouldRetornarReceitasPorUsuarioId() {
        ReceitaEntity entity = ReceitaEntity.builder()
                .id(1L)
                .nome("Arroz")
                .descricao("Simples")
                .categoria(Categoria.FITNESS)
                .usuario(UsuarioEntity.builder().id(1L).build())
                .build();

        when(receitaRepository.findByUsuarioId(1L)).thenReturn(List.of(entity));

        List<Receita> receitas = receitaJpaAdapter.getByUsuarioId(1L);

        assertEquals(1, receitas.size());
        assertEquals("Arroz", receitas.get(0).getNome());
        assertEquals(Categoria.FITNESS, receitas.get(0).getCategoria());
    }

    @Test
    void shouldRetornarReceitaPorIdComUsuarioCorreto() {
        ReceitaEntity receitaEntity = ReceitaEntity.builder()
                .id(1L)
                .nome("Feijão")
                .descricao("Com arroz")
                .categoria(Categoria.FITNESS)
                .build();

        UsuarioEntity usuarioEntity = UsuarioEntity.builder()
                .id(10L)
                .receitas(Set.of(receitaEntity))
                .build();

        receitaEntity.setUsuario(usuarioEntity); // necessário após build

        when(usuarioRepository.findByReceitaId(1L)).thenReturn(Optional.of(usuarioEntity));

        Receita receita = receitaJpaAdapter.getReceitaById(10L, 1L);

        assertEquals(1L, receita.getId());
        assertEquals("Feijão", receita.getNome());
    }

    @Test
    void shouldThrowUnauthorizedExceptionSeUsuarioDiferente() {
        ReceitaEntity receitaEntity = ReceitaEntity.builder()
                .id(1L)
                .nome("Feijão")
                .categoria(Categoria.SALGADO)
                .build();

        UsuarioEntity donoDaReceita = UsuarioEntity.builder()
                .id(99L)
                .receitas(Set.of(receitaEntity))
                .build();

        receitaEntity.setUsuario(donoDaReceita); // necessário após build

        when(usuarioRepository.findByReceitaId(1L)).thenReturn(Optional.of(donoDaReceita));

        assertThrows(UnauthorizedException.class,
                () -> receitaJpaAdapter.getReceitaById(10L, 1L)); // usuário 10 tentando acessar
    }

    @Test
    void shouldThrowNotFoundExceptionSeReceitaNaoExiste() {
        when(usuarioRepository.findByReceitaId(100L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> receitaJpaAdapter.getReceitaById(1L, 100L));

        assertTrue(exception.getMessage().contains("100"));
    }

    @Test
    void shouldDeletarReceita() {
        Receita receita = Receita.builder()
                .id(5L)
                .nome("Salada")
                .descricao("Simples e rápida")
                .categoria(Categoria.BEBIDA)
                .build();

        assertDoesNotThrow(() -> receitaJpaAdapter.deletar(receita));

        verify(receitaRepository, times(1)).delete(any(ReceitaEntity.class));
    }
}
