package br.com.ucb.book.application.controller;

import br.com.ucb.book.application.dto.response.UsuarioResponse;
import br.com.ucb.book.application.mapper.UsuarioDtoMapper;
import br.com.ucb.book.domain.service.UsuarioService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "security_auth")
@Tag(name = "Controller de usuário", description = "Endpoints relacionados ao usuário autenticado")
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final UsuarioDtoMapper usuarioDtoMapper;

    @GetMapping
    @Operation(summary = "Buscar dados do usuário", description = "Retorna os dados do usuário autenticado com base no token JWT.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dados do usuário retornados com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponse getUsuarioByToken(Authentication authentication) {
        Jwt token = (Jwt) authentication.getPrincipal();
        return usuarioDtoMapper.toResponse(usuarioService.findById(token.getClaim("id")));
    }

    @Hidden
    @PostMapping("/confirma-email")
    @ResponseStatus(HttpStatus.OK)
    public void confirmaEmail(Authentication authentication) {
        Jwt token = (Jwt) authentication.getPrincipal();
        usuarioService.confirmaEmail(token.getClaim("id"));
    }
}
