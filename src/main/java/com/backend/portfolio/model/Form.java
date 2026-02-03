package com.backend.portfolio.model;

import jakarta.validation.constraints.*;

public class Form {

    @NotBlank(message = "Name is required.")
    @Size(max = 50, message = "Name cannot be longer than 50 characters.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Size(max = 50, message = "Email cannot be longer than 50 characters.")
    @Email(message = "Must be a valid email.")
    private String email;
    
    @NotBlank(message = "Subject is required")
    @Size(max = 50, message = "Subject cannot be longer than 50 characters.")
    private String subject;

    @NotBlank(message = "Message cannot be empty.")
    @Size(max = 1000, message = "Message too long.")
    private String message;

    
    public Form() { }
    
    public Form(String name, String email, String subject, String message) {
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }


    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getSubject() { return subject; }
    public String getMessage() { return message; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setSubject(String subject) { this.subject = subject; }
    public void setMessage(String message) { this.message = message; }

}
