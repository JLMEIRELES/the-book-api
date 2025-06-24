package br.com.ucb.book.application.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FieldErrorResponse {

    private String campo;
    private String mensagem;
}

