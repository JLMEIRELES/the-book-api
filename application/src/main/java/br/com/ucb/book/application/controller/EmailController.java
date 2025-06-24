package br.com.ucb.book.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/permitted/email")
@Controller
public class EmailController {

    @GetMapping("/confirma-email")
    @ResponseStatus(HttpStatus.OK)
    public String confirmaEmail(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "atualiza-email";
    }
}
