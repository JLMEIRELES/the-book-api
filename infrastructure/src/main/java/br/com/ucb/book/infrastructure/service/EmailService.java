package br.com.ucb.book.infrastructure.service;

import br.com.ucb.book.infrastructure.entity.UsuarioEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    private final TemplateService templateService;

    public void enviarConfirmacaoEmail(UsuarioEntity usuario, String token) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(usuario.getEmail());
        helper.setSubject("Confirmação de Cadastro");
        helper.setFrom("thebookjoaa@gmail.com");
        String corpoHtml = templateService.processarConfirmacaoEmail(usuario.getNome(), token);
        helper.setText(corpoHtml, true);

        javaMailSender.send(mimeMessage);
    }
}
