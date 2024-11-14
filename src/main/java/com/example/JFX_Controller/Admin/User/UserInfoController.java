package com.example.JFX_Controller.Admin.User;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

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
    @FXML private AnchorPane userPane;

    @FXML
    void turnOffPane(ActionEvent event) {
        userPane.getChildren().remove(this.userInfoRoot);
        this.userInfoRoot = null;
    }

    public void setInfo(Client client, AnchorPane userPane) {
        this.userId.setText(String.valueOf(client.getId()));
        this.userName.setText(client.getUsername());
        this.email.setText(client.getEmail());
        this.phone.setText(client.getPhoneNumber());
        this.age.setText(String.valueOf(client.getAge()));
        this.borrowed.setText(String.valueOf(client.getBorrowedBook()));
        this.userPane = userPane;
    }
}
