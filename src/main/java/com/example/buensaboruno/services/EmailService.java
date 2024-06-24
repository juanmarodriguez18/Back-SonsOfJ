package com.example.buensaboruno.services;

import jakarta.mail.MessagingException;

public interface EmailService {

    public void facturaPorEmail(String to, byte[] pdfContent) throws MessagingException;

}
