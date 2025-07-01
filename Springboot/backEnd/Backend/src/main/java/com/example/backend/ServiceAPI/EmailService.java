package com.example.backend.ServiceAPI;

import com.example.backend.Entities.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final JwtService jwtService;

    public EmailService(JavaMailSender mailSender, JwtService jwtService) {
        this.mailSender = mailSender;
        this.jwtService = jwtService;
    }

    public void sendVerificationEmail(User user) throws MessagingException {
        String token = jwtService.generateToken(user);
        String verificationLink = "http://localhost:8088/api/auth/verify?token=" + token;

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("shayma.haouas@gmail.com");
        helper.setTo(user.getEmail());
        helper.setSubject("Vérifiez votre e-mail");
        helper.setText(
                "<p>Cliquez sur le lien suivant pour vérifier votre compte :</p>" +
                        "<a href='" + verificationLink + "'>Vérifier mon compte</a>",
                true
        );

        mailSender.send(message);
    }

    public void sendPasswordResetEmail(User user) {
        String token = jwtService.generateToken(user);
        String resetLink = "http://localhost:4200/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("shayma.haouas@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Réinitialisation de votre mot de passe");
        message.setText("Cliquez sur le lien suivant pour réinitialiser votre mot de passe : " + resetLink);
        mailSender.send(message);
    }

    public void sendReponseEmail(User user, String messageReponse) {
        if (user == null || user.getEmail() == null || user.getEmail().isEmpty()) {
            System.out.println("L'utilisateur n'a pas d'e-mail valide.");
            return;
        }

        System.out.println("Envoi de l'e-mail à : " + user.getEmail());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("shayma.haouas@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Réponse à votre réclamation");
        message.setText("Bonjour, \n\nVotre réclamation a reçu une réponse : \n\n" + messageReponse);

        mailSender.send(message);
        System.out.println("E-mail envoyé avec succès.");
    }
}
