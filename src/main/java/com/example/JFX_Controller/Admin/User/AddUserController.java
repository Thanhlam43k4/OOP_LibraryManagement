package com.example.JFX_Controller.Admin.User;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import com.example.Handlers.Notify;
import com.example.Handlers.Validate;
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
        // validate
        String email_input = email.getText();
        String userName_input = userName.getText();
        String phone_input = phone.getText();
        String age_input = age.getText();

        if (!Validate.isValidEmail(email_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Eror", "Email invalid!");
            return;
        }

        if (!Validate.isValidTitle(userName_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Eror", "Username invalid!");
            return;
        }
        // update ui
        AdminController.instance.userPane.getChildren().remove(addUserRoot);
        addUserRoot = null;
    }
    @FXML
    void cancelAddUser(ActionEvent event) {
        AdminController.instance.userPane.getChildren().remove(addUserRoot);
        addUserRoot = null;
    }
}