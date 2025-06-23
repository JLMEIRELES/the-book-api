package br.com.ucb.book.infrastructure.entity;

import br.com.ucb.book.domain.model.TipoPessoa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEntity extends Auditable implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    @Column(unique = true)
    private String documento;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario")
    @Builder.Default
    private TipoUsuario tipoUsuario = TipoUsuario.CLIENTE;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa")
    private TipoPessoa tipoPessoa;

    @NotNull
    private String senha;

    private LocalDateTime deletadoEm;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private Set<ReceitaEntity> receitas;

    @Column(name = "email_confirmado")
    private boolean emailConfirmado;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(this.getTipoUsuario());
    }

    @Override
    public String getPassword() {
        return this.getSenha();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return this.deletadoEm == null && emailConfirmado;
    }
}
