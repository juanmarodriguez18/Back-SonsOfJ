package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void facturaPorEmail(String to, byte[] pdfContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Tu factura");
        helper.setText("Adjunto encontrarás tu factura.");

        helper.addAttachment("factura.pdf", new ByteArrayResource(pdfContent));

        mailSender.send(message);
        System.out.println("helper.getEncoding() = " + helper.getEncoding());
    }

}
