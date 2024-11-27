package com.example.JFX_Controller.Admin.User;

import com.example.JFX_Controller.Admin.Document.DocRowController;
import com.example.Model.Client;
import com.example.Model.User;
import com.example.Service.UserService;
import com.example.Handlers.ExtraFunction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import com.example.Handlers.Notify;
import com.example.Handlers.Validate;
import com.example.JFX_Controller.Admin.AdminController;
import javafx.event.ActionEvent;

import java.io.IOException;

/**
 * Controller class responsible for handling user interactions when adding a new user
 * in the Admin interface. This class includes input validation and creates a new user
 * after the validation passes. It also handles UI updates after a user is added or
 * when the operation is canceled.
 */
public class AddUserController {

    @FXML private TextField email;        // Input field for the user's email
    @FXML private TextField userName;     // Input field for the user's username
    @FXML private TextField phone;        // Input field for the user's phone number
    @FXML private TextField age;          // Input field for the user's age

    @FXML private StackPane addUserRoot; // Root container for the Add User UI

    /**
     * This method is called when the "Add User" button is clicked. It validates the input
     * fields (email, username, phone number, age), and if valid, creates a new user and
     * adds them to the system. If the validation fails, an error message is shown.
     *
     * @param event The action event triggered when the "Add User" button is clicked
     */
    @FXML
    void addUser(ActionEvent event) {
        // Get input values
        String email_input = email.getText();
        String userName_input = userName.getText();
        String phone_input = phone.getText();
        int age_input = Integer.parseInt(age.getText());

        // Validate the email input
        if (!Validate.isValidEmail(email_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Email is invalid! Please enter a valid email (e.g., example@domain.com).");
            return;
        }

        // Validate the username input
        if (!Validate.isValidUsername(userName_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Username is invalid! Username must be at least 3 characters long and contain only letters, numbers, or underscores.");
            return;
        }

        // Validate the phone number input
        if (!Validate.isValidPhoneNumber(phone_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Phone number is invalid! Please enter a valid phone number (e.g., +1234567890).");
            return;
        }

        // Validate the age input
        if (!Validate.isValidAge(String.valueOf(age_input))) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Age is invalid! Please enter an age between 18 and 120.");
            return;
        }

        // Check if the email already exists in the system
        if(UserService.instance.isEmailExists(email_input)){
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Email is already existed!! Please use another email");
            return;
        } else {
            // Create a new user and add them to the system
            String password = ExtraFunction.encode("1");
            User user = new User(email_input, userName_input,password, phone_input, age_input);
            UserService.instance.addUser(user);
            // getClientByEmal
            //loadUserNode(UserService.instance.getUserByEmail(email_input));
        }

        // Remove the Add User pane from the UI after a successful operation
        AdminController.instance.userPane.getChildren().remove(addUserRoot);
        addUserRoot = null;
    }

    /**
     * This method is called when the "Cancel" button is clicked. It closes the current Add User
     * UI and returns the user to the previous screen without making any changes.
     *
     * @param event The action event triggered when the "Cancel" button is clicked
     */
    @FXML
    void cancelAddUser(ActionEvent event) {
        // Remove the Add User pane from the UI without adding a new user
        AdminController.instance.userPane.getChildren().remove(addUserRoot);
        addUserRoot = null;
    }

    void loadUserNode(Client user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/UserRow.fxml"));
            Parent userNode = loader.load();
            UserRowController docRowController = loader.getController();
            docRowController.setInfo(user);
            AdminController.userList.add(userNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
