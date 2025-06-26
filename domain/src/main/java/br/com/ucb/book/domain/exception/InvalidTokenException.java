package br.com.ucb.book.domain.exception;

import br.com.ucb.book.domain.messages.Messages;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super(Messages.TOKEN_INVALIDO);
    }
}
