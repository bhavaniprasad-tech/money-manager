package com.bhavaniprasad.moneymanager.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BrevoEmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${brevo.sender.email}")
    private String fromEmail;

    @Value("${brevo.sender.name}")
    private String fromName;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public void init() {
        System.out.println("🔥 BrevoEmailService LOADED — SMTP IS DEAD 🔥");
    }

    public void sendEmail(String to, String subject, String htmlBody) {

        System.out.println("🔥 Sending email via BREVO API 🔥");

        try {
            String url = "https://api.brevo.com/v3/smtp/email";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", apiKey);

            String body = """
                {
                  "sender": {
                    "name": "%s",
                    "email": "%s"
                  },
                  "to": [
                    { "email": "%s" }
                  ],
                  "subject": "%s",
                  "htmlContent": "%s"
                }
                """.formatted(
                    fromName,
                    fromEmail,
                    to,
                    subject,
                    htmlBody.replace("\"", "\\\"")
            );

            HttpEntity<String> request = new HttpEntity<>(body, headers);
            restTemplate.postForEntity(url, request, String.class);

        } catch (Exception e) {
            System.err.println("❌ Brevo email failed: " + e.getMessage());
        }
    }
}
