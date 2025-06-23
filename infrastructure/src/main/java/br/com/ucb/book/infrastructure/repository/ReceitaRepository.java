package br.com.ucb.book.infrastructure.repository;

import br.com.ucb.book.infrastructure.entity.ReceitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<ReceitaEntity, Long> {

    @Query("SELECT r FROM UsuarioEntity u " +
            "INNER JOIN u.receitas r " +
            "WHERE u.id = :usuarioId")
    List<ReceitaEntity> findByUsuarioId(Long usuarioId);
}
