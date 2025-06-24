package br.com.ucb.book.infrastructure.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateEngine templateEngine;

    public String processarConfirmacaoEmail(String nome, String token) {
        Context context = new Context();
        context.setVariable("nome", nome);
        context.setVariable("token", token);
        return templateEngine.process("confirma-email", context);
    }
}

