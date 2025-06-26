package br.com.ucb.book.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Credenciais para autenticação do usuário")
public class LoginRequest {

    @Schema(description = "E-mail ou nome de usuário", example = "joao@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "Senha do usuário", example = "1234567809", requiredMode = Schema.RequiredMode.REQUIRED)
    private String senha;
}
