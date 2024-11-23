package com.example.Service;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;


public class EmailService {

    private static final String SMTP_HOST = "smtp.gmail.com"; // Ví dụ với Gmail
    private static final int SMTP_PORT = 465; // Port SSL
    private static final String SMTP_USERNAME = "nguyenthanhlam71204@gmail.com"; // Thay bằng email của bạn
    private static final String SMTP_PASSWORD = "siwhigbxgdzjmvox"; // Thay bằng mật khẩu email của bạn
    private static EmailService instance;

    // Bước 3: Tạo constructor private để ngăn việc khởi tạo từ ngoài lớp
    private EmailService() {}

    // Bước 4: Cung cấp phương thức getInstance để lấy thể hiện duy nhất của lớp
    public static synchronized EmailService getInstance() {
        if (instance == null) {
            instance = new EmailService();
        }
        return instance;
    }
    /**
     * Gửi email HTML với nội dung là một chuỗi HTML.
     * @param to Địa chỉ email người nhận
     * @param subject Tiêu đề email
     * @param htmlContent Nội dung HTML của email
     * @throws EmailException nếu có lỗi khi gửi email
     */
    public void sendHtmlEmail(String to, String subject, String htmlContent) throws EmailException, EmailException {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(SMTP_HOST);
        email.setSmtpPort(SMTP_PORT);
        email.setAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
        email.setSSLOnConnect(true); // Bật SSL

        email.setFrom(SMTP_USERNAME);
        email.addTo(to);
        email.setSubject(subject);
        email.setMsg(htmlContent);

        // Gửi email
        email.send();
    }

    /**
     * Gửi email đơn giản với nội dung dạng văn bản
     * @param to Địa chỉ email người nhận
     * @param subject Tiêu đề email
     * @param message Nội dung email
     * @throws EmailException nếu có lỗi khi gửi email
     */
    public void sendTextEmail(String to, String subject, String message) throws EmailException {
        Email email = new HtmlEmail();
        email.setHostName(SMTP_HOST);
        email.setSmtpPort(SMTP_PORT);
        email.setAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
        email.setSSLOnConnect(true);

        email.setFrom(SMTP_USERNAME);
        email.addTo(to);
        email.setSubject(subject);
        email.setMsg(message);

        // Gửi email
        email.send();
    }

}