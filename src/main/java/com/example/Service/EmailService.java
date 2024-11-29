package com.example.Service;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * The EmailService class provides methods to send emails using SMTP.
 * It supports sending both HTML-formatted emails and plain text emails.
 * This class uses Gmail's SMTP server to send emails.
 * It follows the Singleton design pattern to ensure only one instance is created.
 */
public class EmailService {

    // SMTP server configurations
    private static final String SMTP_HOST = "smtp.gmail.com"; // Example: Gmail SMTP server
    private static final int SMTP_PORT = 465; // SSL Port
    private static final String SMTP_USERNAME = "nguyenthanhlam71204@gmail.com"; // Replace with your email
    private static final String SMTP_PASSWORD = "siwhigbxgdzjmvox"; // Replace with your email password

    // Singleton instance
    private static EmailService instance;

    /**
     * Private constructor to prevent external instantiation of the class.
     * The singleton pattern ensures only one instance of EmailService is used.
     */
    EmailService() {}

    /**
     * Returns the singleton instance of the EmailService class.
     * If the instance does not exist, it will be created.
     *
     * @return The singleton instance of EmailService.
     */
    public static synchronized EmailService getInstance() {
        if (instance == null) {
            instance = new EmailService();
        }
        return instance;
    }

    /**
     * Sends an HTML email with the specified content.
     *
     * @param to The recipient's email address.
     * @param subject The subject of the email.
     * @param htmlContent The HTML content of the email.
     * @throws EmailException if an error occurs while sending the email.
     */
    public void sendHtmlEmail(String to, String subject, String htmlContent) throws EmailException {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(SMTP_HOST);
        email.setSmtpPort(SMTP_PORT);
        email.setAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
        email.setSSLOnConnect(true); // Enable SSL for secure connection

        email.setFrom(SMTP_USERNAME);
        email.addTo(to);
        email.setSubject(subject);
        email.setMsg(htmlContent);

        // Send the email
        email.send();
    }

    /**
     * Sends a plain text email with the specified content.
     *
     * @param to The recipient's email address.
     * @param subject The subject of the email.
     * @param message The plain text content of the email.
     * @throws EmailException if an error occurs while sending the email.
     */
    public void sendTextEmail(String to, String subject, String message) throws EmailException {
        Email email = new HtmlEmail();
        email.setHostName(SMTP_HOST);
        email.setSmtpPort(SMTP_PORT);
        email.setAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
        email.setSSLOnConnect(true); // Enable SSL for secure connection

        email.setFrom(SMTP_USERNAME);
        email.addTo(to);
        email.setSubject(subject);
        email.setMsg(message);

        // Send the email
        email.send();
    }
}
