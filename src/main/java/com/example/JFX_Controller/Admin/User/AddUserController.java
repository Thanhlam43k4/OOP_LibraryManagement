package com.example.JFX_Controller.Admin.User;

import com.example.Model.User;
import com.example.Service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import com.example.Handlers.Notify;
import com.example.Handlers.Validate;
import com.example.JFX_Controller.Admin.AdminController;

import javafx.event.ActionEvent;
import org.mockito.internal.matchers.Not;

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
        int age_input = Integer.parseInt(age.getText());

        if (!Validate.isValidEmail(email_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Email is invalid! Please enter a valid email (e.g., example@domain.com).");
            return;
        }
        if (!Validate.isValidUsername(userName_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Username is invalid! Username must be at least 3 characters long and contain only letters, numbers, or underscores.");
            return;
        }
        if (!Validate.isValidPhoneNumber(phone_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Phone number is invalid! Please enter a valid phone number (e.g., +1234567890).");
            return;
        }
        if (!Validate.isValidAge(String.valueOf(age_input))) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Age is invalid! Please enter an age between 18 and 120.");
            return;
        }
        if(UserService.instance.isEmailExists(email_input)){
            Notify.showAlert(Alert.AlertType.ERROR,"Error", "Email is already existed!! Please use another email");
            return;
        }else{
            User user = new User(email_input,userName_input,phone_input,age_input);
            UserService.instance.createUser(user);

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