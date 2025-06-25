package br.com.ucb.book.infrastructure.repository;

import br.com.ucb.book.infrastructure.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    @Query("SELECT u FROM UsuarioEntity u WHERE u.documento = :documento")
    Optional<UsuarioEntity> findByDocumento(String documento);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.email = :username OR u.documento = :username")
    Optional<UsuarioEntity> findByUsername(String username);

    @Query("SELECT u FROM UsuarioEntity u " +
            "INNER JOIN FETCH u.receitas r " +
            "WHERE r.id = :idReceita")
    Optional<UsuarioEntity> findByReceitaId(Long idReceita);

    @Modifying
    @Query("UPDATE UsuarioEntity u SET u.emailConfirmado = true WHERE u.id = :id")
    int confirmarEmail(Long id);
}
