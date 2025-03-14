package com.example.filedivider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoConAdjuntos(String destinatario, String asunto, String cuerpo, File[] archivosAdjuntos)
            throws MessagingException {

        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

        helper.setTo(destinatario);
        helper.setSubject(asunto);
        helper.setText(cuerpo);

        // Adjuntar los archivos
        for (File archivo : archivosAdjuntos) {
            FileSystemResource recurso = new FileSystemResource(archivo);
            helper.addAttachment(archivo.getName(), recurso);
        }

        mailSender.send(mensaje);
    }
}
