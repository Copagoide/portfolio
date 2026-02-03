package com.backend.portfolio.controller;

import com.backend.portfolio.model.Form;
import jakarta.validation.Valid;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequestMapping("/contact")
@CrossOrigin(origins = "https://gustavocopaga-portfolio.netlify.app")
public class FormController {

    private final JavaMailSender mailSender;
    private final String receiverEmail; 

    public FormController(JavaMailSender mailSender, @Value("${contact.receiver-email-address}") String receiverEmail) {
        this.mailSender = mailSender;
        this.receiverEmail = receiverEmail;
    }

    @PostMapping
    public ResponseEntity<String> submitForm(@Valid @RequestBody Form form) {
        
    	// Prevent email header injection
    	if (containsInvalidHeaderChars(form.getName()) ||
		    containsInvalidHeaderChars(form.getEmail()) ||
		    containsInvalidHeaderChars(form.getSubject())) {
		    return ResponseEntity.badRequest().body("Invalid characters in name, email, or subject.");
		}


        // Escape HTML to prevent XSS
        String safeName = HtmlUtils.htmlEscape(form.getName());
        String safeEmail = HtmlUtils.htmlEscape(form.getEmail());
        String safeSubject = HtmlUtils.htmlEscape(form.getSubject());
        String safeMessage = HtmlUtils.htmlEscape(form.getMessage());
    	
    	// Send email
    	SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiverEmail);
        message.setSubject("PORTFOLIO MESSAGE FROM: " + safeName);
        message.setText(
            "Name: " + safeName + "\n" +
            "Email: " + safeEmail + "\n" +
            "Subject: " + safeSubject + "\n" +
            "Message:\n\n" + safeMessage
        );

        mailSender.send(message);

        return ResponseEntity.ok("Message sent successfully!");
    }
    
    private boolean containsInvalidHeaderChars(String input) {
        if (!StringUtils.hasText(input)) return false;
        return input.contains("\n") || input.contains("\r");
    }
}
