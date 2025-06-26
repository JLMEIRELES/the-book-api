package br.com.ucb.book.application.controller;

import br.com.ucb.book.application.dto.request.ReceitaRequest;
import br.com.ucb.book.application.dto.response.ReceitaData;
import br.com.ucb.book.application.dto.response.ReceitasResponse;
import br.com.ucb.book.application.mapper.ReceitaDtoMapper;
import br.com.ucb.book.domain.model.Receita;
import br.com.ucb.book.domain.service.ReceitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receita")
@SecurityRequirement(name = "security_auth")
@RequiredArgsConstructor
@Tag(name = "Controller de Receitas", description = "Endpoints protegidos para gerenciamento de receitas")
public class ReceitaController {

    private final ReceitaService receitaService;

    private final ReceitaDtoMapper receitaDtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar receita", description = "Cria uma nova receita para o usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Receita criada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public void criar(
            @Valid @RequestBody
            ReceitaRequest receitaRequest,
            Authentication authentication
    ) {
        Long idUsuario = getIdUsuario(authentication);
        receitaService.criar(receitaDtoMapper.toModel(receitaRequest, idUsuario));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar receita por ID", description = "Retorna os dados de uma receita do usuário autenticado.")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Receita encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada")
    })
    public ReceitaData getReceitaById(
            @Parameter(description = "ID da receita a ser buscada", required = true)
            @PathVariable("id") Long idReceita,

            Authentication authentication) {
        Long idUsuario = getIdUsuario(authentication);
        return receitaDtoMapper.toData(receitaService.getReceitaById(idUsuario, idReceita));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar receita", description = "Edita os dados de uma receita do usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Receita editada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada")
    })
    @ResponseStatus(HttpStatus.OK)
    public void editar(
            @Parameter(description = "ID da receita a ser editada", required = true)
            @PathVariable("id") Long idReceita,

            @RequestBody ReceitaRequest receitaRequest,

            Authentication authentication) {
        Long idUsuario = getIdUsuario(authentication);
        Receita receita = receitaDtoMapper.toReceitaWithUsuario(receitaRequest, idReceita, idUsuario);
        receitaService.editarReceita(receita);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar receita", description = "Exclui uma receita do usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Receita excluída com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(
            @Parameter(description = "ID da receita a ser deletada", required = true)
            @PathVariable("id") Long idReceita,

            Authentication authentication) {
        Long idUsuario = getIdUsuario(authentication);
        receitaService.deletar(idReceita, idUsuario);
    }

    @GetMapping("/minhasReceitas")
    @Operation(summary = "Listar minhas receitas", description = "Retorna todas as receitas do usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Receitas retornadas com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @ResponseStatus(HttpStatus.OK)
    public ReceitasResponse minhasReceitas(Authentication authentication) {
        Long idUsuario = getIdUsuario(authentication);
        return receitaDtoMapper.receitasToReceitasResponse(receitaService.getRecetasByUsuarioId(idUsuario));
    }

    private Long getIdUsuario(Authentication authentication) {
        Jwt token =  (Jwt) authentication.getPrincipal();
        return token.getClaim("id");
    }
}
