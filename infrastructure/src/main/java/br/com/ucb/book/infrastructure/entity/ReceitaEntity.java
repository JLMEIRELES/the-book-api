package br.com.ucb.book.infrastructure.entity;

import br.com.ucb.book.domain.model.Categoria;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Builder
@Table(name = "receita")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceitaEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_receita")
    private Long id;

    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
}
