package com.example.Service;

import com.example.Interface.UserDao;
import com.example.DAO.UserDaoImpl;
import com.example.Model.User;

import java.sql.Connection;
import java.util.List;

public class UserService {
    public static UserService instance;
    private UserDao userDao;

    public UserService(Connection connection) {
        userDao = new UserDaoImpl(connection);
    }

    public void createUser(User user) {
        userDao.createUser(user);
    }

    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
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
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }


}
