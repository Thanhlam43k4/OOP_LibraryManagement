package com.example.JFX_Controller;

//#region Lib
import java.util.Objects;

import com.example.Handlers.ErrorHandler;
import com.example.Model.User;
import com.example.Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
//#endregion

public class SignInUpController extends Controller {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmpassField;

    @FXML
    void returnSignIn(ActionEvent event) {
        loadScene("Login.fxml");
    }

    @FXML
    void goCreateAcc(ActionEvent event) {
        loadScene("Signup.fxml");
    }

    @FXML
    void signIn(ActionEvent event) {
        // loadScene("Admin.fxml");
        be_signIn();
    }

    @FXML
    void signUp(ActionEvent event) {
        be_signUp();
    }


    private void be_signIn() {
        String email = emailField.getText();
        String password = passwordField.getText();
        boolean isMatchAccount = UserService.instance.isMatchAccount(email, password);
        if (!isMatchAccount) {
            ErrorHandler.showAlert(Alert.AlertType.INFORMATION, "Email or password is wrong", "Please login again!");
        } else {
            System.out.println("Login Successfully with email: " + email);
            loadScene("Client.fxml");
        }
    }
    private void be_signUp() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmpassword = confirmpassField.getText();
        boolean isEmailExists = UserService.instance.isEmailExists(email);
        if (email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()) {
            ErrorHandler.showAlert(Alert.AlertType.INFORMATION, "Missing Information", "Please fill in all fields!");
        } else if (isEmailExists) {
            ErrorHandler.showAlert(Alert.AlertType.INFORMATION, "Email is existed", "Please register with other email!");
            loadScene("Login.fxml");
        } else if (Objects.equals(password, confirmpassword)) {
            User user = new User(email, password);
            UserService.instance.createUser(user);
            ErrorHandler.showAlert(Alert.AlertType.INFORMATION, "Register Successful", "Please Login!");
            loadScene("Login.fxml");
        } else {
            ErrorHandler.showAlert(Alert.AlertType.INFORMATION, "Wrong Password Matching", "Please register again!");
        }
    }
}
