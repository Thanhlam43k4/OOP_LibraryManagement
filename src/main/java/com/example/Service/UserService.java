package com.example.Service;

import com.example.Interface.UserDao;
import com.example.DAO.UserDaoImpl;
import com.example.Model.Client;
import com.example.Model.User;
import org.apache.commons.mail.EmailException;
import com.example.Handlers.*;
import java.sql.Connection;
import java.util.List;

public class UserService {
    public static UserService instance;
    private final UserDao userDao;

    public UserService(Connection connection) {
        userDao = new UserDaoImpl(connection);
    }

    public void createUser(User user) {
        userDao.createUser(user);
    }

    public void addUser(User user) { userDao.addUser(user);}
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    public List<Client> getAllClients() {
        return userDao.getAllClients();
    }

    public void updateUser(int userId,String username,String phoneNumber, int age) {
        userDao.updateUser(userId,username,phoneNumber,age);
    }

    public boolean isEmailExists(String email){
        return userDao.isEmailExists(email);
    }

    public boolean isMatchAccount(String email, String password){
        return userDao.isMatchAccount(email,password);
    }

    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    public int getUserBooks(int id) {
       return userDao.getUserBooks(id);
    }
    public void updatePassword(int userId,String updatePassword){
        userDao.updatePassword(userId,updatePassword);
    }

    public String forgotPassword(String email) {
        String verificationCode = ExtraFunction.generateVerificationCode();

        String subject = "Password Reset Request";
        String htmlContent = "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; color: #333; }" +
                "h3 { font-size: 24px; color: #5cb85c; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<p>Hello,</p>" +
                "<p>To reset your password, please enter the following verification code:</p>" +
                "<h3>" + verificationCode + "</h3>" +
                "<p>Have a nice day!</p>" +
                "</body>" +
                "</html>";
        try {
            EmailService.getInstance().sendHtmlEmail(email, subject, htmlContent);
            System.out.println("Email gửi thành công đến: " + email);
            return verificationCode;
        } catch (EmailException e) {
            System.err.println("Lỗi khi gửi email: " + e.getMessage());
            return "";
        }
    }
}
