package br.com.ucb.book.application.handler;

import br.com.ucb.book.domain.exception.ConflictException;
import br.com.ucb.book.domain.exception.NotFoundException;
import br.com.ucb.book.domain.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ExceptionHandlerAdvice {

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGeneralException(Exception ex) {
        log.error("handleGeneralException", ex);
        return ErrorResponse.builder()
                .error("Ocorreu um erro de servidor")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .details(ex.getMessage())
                .build();
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("handleMethodArgumentNotValid", ex);
        var fieldErrors = ex.getFieldErrors()
                .stream()
                .map(fieldError ->
                        new FieldErrorResponse(fieldError.getField(), fieldError.getDefaultMessage())
                ).toList();

        return ErrorResponse.builder()
                .fieldErrors(fieldErrors)
                .details(ex.getMessage())
                .error("Ocorreu um erro ao processar requisição")
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .build();
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public ErrorResponse handleConflictException(ConflictException ex) {
        log.error("handleConflictException", ex);
        var fieldErrors = ex.getCampos()
                .stream()
                .map(campo ->
                        new FieldErrorResponse(campo, ex.getMessage())
                ).toList();
        return ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .error("Ocorreu um conflito ao processar requisição")
                .details(ex.getMessage())
                .fieldErrors(fieldErrors)
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ErrorResponse handleUnauthorizedException(UnauthorizedException ex) {
        log.error("handleUnauthorizedException", ex);
        return ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("Unauthorized")
                .details(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundException(NotFoundException ex) {
        log.error("handleNotFoundException", ex);
        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error("Não encontrado")
                .details(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(DisabledException.class)
    public ErrorResponse handleDisabledException(DisabledException ex) {
        log.error("handleDisableException", ex);
        return ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("Usuário não confirmou email")
                .details(ex.getMessage())
                .build();
    }
}