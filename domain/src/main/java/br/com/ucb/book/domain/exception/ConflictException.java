package br.com.ucb.book.domain.exception;

import br.com.ucb.book.domain.messages.Messages;
import lombok.Getter;

import java.util.List;

@Getter
public class ConflictException extends RuntimeException {

    private List<String> campos;

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(List<String> campos) {
        super(Messages.USUARIO_EXISTENTE);
        this.campos = campos;
    }
}
