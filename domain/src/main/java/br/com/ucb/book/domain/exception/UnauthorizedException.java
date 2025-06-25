package br.com.ucb.book.domain.exception;

import br.com.ucb.book.domain.messages.Messages;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super(Messages.UNAUTHORIZED);
    }
}
