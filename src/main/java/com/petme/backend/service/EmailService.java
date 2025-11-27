package com.petme.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoRecuperacion(String destinatario, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("petmesoporte@gmail.com");
        message.setTo(destinatario);
        message.setSubject("Recupera tu contraseña en PetMe");
        message.setText("Hola,\n\nHaz clic en el siguiente enlace para cambiar tu contraseña:\n" +
                "http://TU_IP_AWS/reset-password.html?token=" + token + "\n\n" +
                "Si no fuiste tú, ignora este mensaje.");

        mailSender.send(message);
    }
}
