package org.example.medtrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Daily combined report email
    public void sendDailySummary(String to, String report) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Daily Medicine Alert Report");
        message.setText(report);

        mailSender.send(message);
    }
}