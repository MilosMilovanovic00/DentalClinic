package com.example.project.configuration.email;

public interface EmailSender {
    void send(String to, String email, String subject);
}
