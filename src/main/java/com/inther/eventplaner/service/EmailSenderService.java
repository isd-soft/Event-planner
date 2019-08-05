package com.inther.eventplaner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String emailRecipient, String emailSubject, String emailText ) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(emailRecipient);

        msg.setSubject(emailSubject);
        msg.setText(emailText);

        javaMailSender.send(msg);
    }
}
