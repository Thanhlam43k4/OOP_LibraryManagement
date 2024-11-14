package com.example.JFX_Controller.Admin.User;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;

public class AddUserController {
    @FXML private TextField email;
    @FXML private TextField userName;
    @FXML private TextField phone;
    @FXML private TextField age;
    
    @FXML private StackPane addUserRoot;
    @FXML private AnchorPane userPane;

    @FXML
    void addUser(ActionEvent event) {
        userPane.getChildren().remove(addUserRoot);
        addUserRoot = null;
    }
    @FXML
    void cancelAddUser(ActionEvent event) {
        userPane.getChildren().remove(addUserRoot);
        addUserRoot = null;
    }

    public void setInfo(AnchorPane userPane) {
        this.userPane = userPane;
    }
}