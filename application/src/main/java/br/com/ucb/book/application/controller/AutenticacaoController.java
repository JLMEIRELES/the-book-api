package br.com.ucb.book.application.controller;

import br.com.ucb.book.application.dto.request.LoginRequest;
import br.com.ucb.book.application.dto.request.UsuarioRequest;
import br.com.ucb.book.application.dto.response.TokenResponse;
import br.com.ucb.book.application.mapper.UsuarioDtoMapper;
import br.com.ucb.book.domain.model.Usuario;
import br.com.ucb.book.domain.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permitted")
@Tag(name = "Controller de autenticação", description = "Endpoints públicos para cadastro e login")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final UsuarioService usuarioService;

    private final UsuarioDtoMapper usuarioDtoMapper;

    @PostMapping("/cadastro")
    @Operation(summary = "Cadastrar novo usuário", description = "Cria uma nova conta de usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrar(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioDtoMapper.toModel(usuarioRequest);
        usuarioService.criar(usuario);
    }

    @PostMapping("/login")
    @Operation(summary = "Realizar login", description = "Realiza login e retorna um token JWT.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido e token retornado",
                    content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        Usuario usuario = usuarioDtoMapper.toModel(loginRequest);
        return usuarioDtoMapper.toTokenResponse(usuarioService.login(usuario));
    }
}
