package br.com.ucb.book.application.dto.request;

import br.com.ucb.book.application.validation.Documento;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioRequest {

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String senha;

    @NotBlank
    private String confirmaSenha;

    @Documento
    @NotBlank
    private String documento;

    @AssertTrue(message = "Senhas não coincidem")
    public boolean isSenha() {
        return senha.equals(confirmaSenha);
    }
}
