package com.example.Interface;

import com.example.Model.Client;
import com.example.Model.User;

import java.util.List;

public interface UserDao {
    void createUser(User user);
    User getUserById(int id);
    List<Client> getAllClients();
    User getUserByEmail(String email);
    void updateUser(User user);
    void deleteUser(int id);
    boolean isEmailExists(String email);
    boolean isMatchAccount(String email, String password);
    void addUser(String email, String username, String phoneNumber, int age);
    int getUserBooks(int id);
}
