package br.com.ucb.book.application.controller;

import br.com.ucb.book.application.dto.response.UsuarioResponse;
import br.com.ucb.book.application.mapper.UsuarioDtoMapper;
import br.com.ucb.book.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final UsuarioDtoMapper usuarioDtoMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponse getUsuarioByToken(Authentication authentication) {
        Jwt token = (Jwt) authentication.getPrincipal();
        return usuarioDtoMapper.toResponse(usuarioService.findById(token.getClaim("id")));
    }

    @PostMapping("/confirma-email")
    @ResponseStatus(HttpStatus.OK)
    public void confirmaEmail(Authentication authentication) {
        Jwt token = (Jwt) authentication.getPrincipal();
        usuarioService.confirmaEmail(token.getClaim("id"));
    }
}
