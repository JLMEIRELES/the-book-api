package br.com.ucb.book.application.dto.request;

import br.com.ucb.book.application.validation.Documento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioRequest {

    @NotBlank
    @Schema(description = "Nome completo do usuário", example = "João Luís Meireles", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome;

    @Email
    @NotBlank
    @Schema(description = "E-mail do usuário", example = "joao@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank
    @Schema(description = "Senha do usuário", example = "12345678", requiredMode = Schema.RequiredMode.REQUIRED)
    private String senha;

    @NotBlank
    @Schema(description = "Confirmação da senha", example = "12345678", requiredMode = Schema.RequiredMode.REQUIRED)
    private String confirmaSenha;

    @Documento
    @NotBlank
    @Schema(description = "CPF ou CNPJ válido", example = "12345678909", requiredMode = Schema.RequiredMode.REQUIRED)
    private String documento;

    @AssertTrue(message = "Senhas não coincidem")
    @Schema(hidden = true)
    public boolean isSenha() {
        return senha.equals(confirmaSenha);
    }
}
