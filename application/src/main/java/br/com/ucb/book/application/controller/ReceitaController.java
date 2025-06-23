package br.com.ucb.book.application.controller;

import br.com.ucb.book.application.dto.request.ReceitaRequest;
import br.com.ucb.book.application.dto.response.ReceitasResponse;
import br.com.ucb.book.application.mapper.ReceitaDtoMapper;
import br.com.ucb.book.domain.service.ReceitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receita")
@RequiredArgsConstructor
public class ReceitaController {

    private final ReceitaService receitaService;

    private final ReceitaDtoMapper receitaDtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criar(
            @Valid @RequestBody
            ReceitaRequest receitaRequest,
            Authentication authentication
    ) {
        Jwt token =  (Jwt) authentication.getPrincipal();
        Long userId = token.getClaim("id");
        receitaService.criar(receitaDtoMapper.toModel(receitaRequest, userId));
    }

    @GetMapping("/minhasReceitas")
    @ResponseStatus(HttpStatus.OK)
    public ReceitasResponse minhasReceitas(Authentication authentication) {
        Jwt token =  (Jwt) authentication.getPrincipal();
        Long userId = token.getClaim("id");
        return receitaDtoMapper.receitasToReceitasResponse(receitaService.getRecetasByUsuarioId(userId));
    }

}
