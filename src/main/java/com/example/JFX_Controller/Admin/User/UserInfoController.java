package com.example.JFX_Controller.Admin.User;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import com.example.JFX_Controller.Admin.AdminController;
import com.example.Model.Client;

import javafx.event.ActionEvent;

public class UserInfoController {
    @FXML private Label userId;
    @FXML private Label userName;
    @FXML private Label email;
    @FXML private Label phone;
    @FXML private Label age;
    @FXML private Label borrowed;
    
    @FXML private StackPane userInfoRoot;

    @FXML
    void turnOffPane(ActionEvent event) {
        AdminController.instance.userPane.getChildren().remove(this.userInfoRoot);
        this.userInfoRoot = null;
    }

    public void setInfo(Client client) {
        this.userId.setText(String.valueOf(client.getId()));
        this.userName.setText(client.getUsername());
        this.email.setText(client.getEmail());
        this.phone.setText(client.getPhoneNumber());
        this.age.setText(String.valueOf(client.getAge()));
        this.borrowed.setText(String.valueOf(client.getBorrowedBook()));
    }
}
