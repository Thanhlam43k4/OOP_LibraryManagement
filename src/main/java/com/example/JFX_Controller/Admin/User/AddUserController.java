package com.example.JFX_Controller.Admin.User;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import com.example.JFX_Controller.Admin.AdminController;

import javafx.event.ActionEvent;

public class AddUserController {
    @FXML private TextField email;
    @FXML private TextField userName;
    @FXML private TextField phone;
    @FXML private TextField age;
    
    @FXML private StackPane addUserRoot;

    @FXML
    void addUser(ActionEvent event) {
        AdminController.instance.userPane.getChildren().remove(addUserRoot);
        addUserRoot = null;
    }
    @FXML
    void cancelAddUser(ActionEvent event) {
        AdminController.instance.userPane.getChildren().remove(addUserRoot);
        addUserRoot = null;
    }
}