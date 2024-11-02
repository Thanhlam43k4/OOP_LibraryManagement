package com.example.Service;

import com.example.Interface.UserDao;
import com.example.DAO.UserDaoImpl;
import com.example.Model.Admin;
import com.example.Model.Client;
import com.example.Model.User;

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

    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    public List<Client> getAllClients() {
        return userDao.getAllClients();
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
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
}
