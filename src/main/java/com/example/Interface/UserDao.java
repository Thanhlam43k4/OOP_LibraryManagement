package com.example.Interface;

import com.example.Model.Client;
import com.example.Model.User;

import java.sql.Date;
import java.util.List;

public interface UserDao {
    void createUser(User user);
    User getUserById(int userId);
    List<Client> getAllClients();
    User getUserByEmail(String email);
    void updateUser(int userId,String username, String phoneNumber, Date dob);
    void deleteUser(int userId);
    boolean isEmailExists(String email);
    boolean isMatchAccount(String email, String password);
    void addUser(String email, String username, String phoneNumber, int age);
    int getUserBooks(int userId);
    void updatePassword(int userId, String updatePass);
}
