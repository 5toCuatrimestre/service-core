package jbar.service_core.Util.Services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/send-reset-code")
    public String sendPasswordResetEmail(
            @RequestParam String to,
            @RequestParam String userName,
            @RequestParam String code,
            @RequestParam(defaultValue = "#007bff") String primaryColor) throws MessagingException {

        emailService.sendPasswordResetEmail(to, userName, code, primaryColor);
        return "Correo de recuperación enviado a " + to;
    }
}

