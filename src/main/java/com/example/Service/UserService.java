package com.example.Service;

import com.example.Interface.UserDao;
import com.example.DAO.UserDaoImpl;
import com.example.Model.Client;
import com.example.Model.User;
import org.apache.commons.mail.EmailException;
import com.example.Handlers.*;
import java.sql.Connection;
import java.util.List;

/**
 * Service class to handle user-related operations.
 * It interacts with the UserDao for data access and email functionalities.
 */
public class UserService {

    // Static instance of UserService for Singleton pattern
    public static UserService instance;
    private final UserDao userDao;

    /**
     * Private constructor to initialize UserDao with a database connection.
     *
     * @param connection the database connection
     */
    public UserService(Connection connection) {
        userDao = new UserDaoImpl(connection);
    }

    /**
     * Creates a new user in the database.
     *
     * @param user the user object to be created
     */
    public void createUser(User user) {
        userDao.createUser(user);
    }

    /**
     * Adds a user to the database.
     *
     * @param user the user object to be added
     */
    public void addUser(User user) {
        userDao.addUser(user);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user
     * @return the user with the given ID
     */
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    /**
     * Retrieves all clients from the database.
     *
     * @return a list of all clients
     */
    public List<Client> getAllClients() {
        return userDao.getAllClients();
    }

    /**
     * Updates a user's information.
     *
     * @param userId the ID of the user
     * @param username the new username
     * @param phoneNumber the new phone number
     * @param age the new age
     */
    public void updateUser(int userId, String username, String phoneNumber, int age) {
        userDao.updateUser(userId, username, phoneNumber, age);
    }

    /**
     * Checks if the email already exists in the database.
     *
     * @param email the email to be checked
     * @return true if the email exists, false otherwise
     */
    public boolean isEmailExists(String email) {
        return userDao.isEmailExists(email);
    }

    /**
     * Checks if the email and password match a valid account.
     *
     * @param email the email to be checked
     * @param password the password to be checked
     * @return true if the email and password match an account, false otherwise
     */
    public boolean isMatchAccount(String email, String password) {
        return userDao.isMatchAccount(email, password);
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user
     * @return the user with the given email
     */
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to be deleted
     */
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    /**
     * Retrieves the number of books associated with a user.
     *
     * @param id the ID of the user
     * @return the number of books the user has
     */
    public int getUserBooks(int id) {
        return userDao.getUserBooks(id);
    }

    /**
     * Updates a user's password.
     *
     * @param userId the ID of the user
     * @param updatePassword the new password to set
     */
    public void updatePassword(int userId, String updatePassword) {
        userDao.updatePassword(userId, updatePassword);
    }

    /**
     * Sends a password reset email to the user with a verification code.
     *
     * @param email the email of the user requesting password reset
     * @return the verification code sent in the email
     */
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
            EmailService emailService = new EmailService();  // Create new instance without getInstance
            emailService.sendHtmlEmail(email, subject, htmlContent);
            System.out.println("Email sent successfully to: " + email);
            return verificationCode;
        } catch (EmailException e) {
            System.err.println("Error sending email: " + e.getMessage());
            return "";
        }
    }

    /**
     * Retrieves a client by their email.
     *
     * @param emailInput the email of the client
     * @return the client with the given email
     */
    public Client getClientByEmail(String emailInput) {
        return userDao.getClientByEmail(emailInput);
    }
}
