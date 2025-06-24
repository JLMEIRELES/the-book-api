package br.com.ucb.book.domain.exception;

import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {

    private String field;

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, String field) {
        super(message);
        this.field = field;
    }
}
