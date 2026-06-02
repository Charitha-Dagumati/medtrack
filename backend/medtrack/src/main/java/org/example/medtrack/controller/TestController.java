package org.example.medtrack.controller;
import org.example.medtrack.service.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
@RestController
public class TestController {

    private final EmailService emailService;

    @Value("${spring.mail.username}")
    private String toEmail;

    public TestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/test-mail")
    public String testMail() {

        String report =
                "Medicine Alert Report\n\n" +
                        "EXPIRING SOON: Paracetamol | Expiry: 2026-06-30\n" +
                        "LOW STOCK: Crocin | Qty: 5";

        emailService.sendDailySummary(
                toEmail,
                report
        );

        return "Daily Summary Mail Sent";
    }
}