package jbar.service_core.Util.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendPasswordResetEmail(String to, String userName, String code, String primaryColor) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String htmlContent = generatePasswordResetTemplate(userName, code, primaryColor);

        helper.setTo(to);
        helper.setSubject("Código de Recuperación de Contraseña");
        helper.setText(htmlContent, true); // `true` indica que es HTML
        helper.setFrom("lossolesutez@gmail.com");

        emailSender.send(message);
    }

    private String generatePasswordResetTemplate(String userName, String code, String primaryColor) {
        String template = """
        <div style="font-family: 'Arial', sans-serif; line-height: 1.6; color: #333; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); background-color: #fff;">
            
            <!-- Encabezado -->
            <div style="background-color: %s; padding: 20px; text-align: center; border-radius: 10px 10px 0 0;">
                <h1 style="color: #fff; font-size: 28px; margin: 0;">Recuperación de Contraseña</h1>
            </div>

            <!-- Contenido Principal -->
            <div style="padding: 20px; text-align: center;">
                <p style="font-size: 16px; color: #666; margin-bottom: 20px;">
                    Hola <strong>%s</strong>,<br />
                    Has solicitado restablecer tu contraseña. Usa el siguiente código dentro de las próximas 24 horas para completar el proceso:
                </p>

                <!-- Sección del Código -->
                <div style="margin: 30px auto; background-color: #f9f9f9; padding: 15px 20px; display: inline-block; border-radius: 5px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);">
                    <span style="font-size: 28px; font-weight: bold; color: %s; letter-spacing: 2px;">%s</span>
                </div>

                <p style="font-size: 14px; color: #999; margin-top: 20px;">
                    Si no solicitaste este restablecimiento, ignora este correo.
                </p>
            </div>

            <!-- Pie de Página -->
            <div style="background-color: #f9f9f9; padding: 20px; text-align: center; border-radius: 0 0 10px 10px;">
                <p style="font-size: 14px; color: #666;">¿Necesitas ayuda? Contáctanos, estamos aquí para ti.</p>
                <p style="font-size: 14px; color: #333;">– Equipo CORE</p>
            </div>
        </div>
    """;

        return String.format(template, primaryColor, userName, primaryColor, code);
    }

}
