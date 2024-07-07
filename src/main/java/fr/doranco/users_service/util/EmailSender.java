package fr.doranco.users_service.util;

public interface EmailSender {
    void sendEmail(String toEmail, String body);
}
