package com.example.JFX_Controller;

//#region Lib
import java.util.Objects;

import com.example.Handlers.Notify;
import com.example.Model.User;
import com.example.Service.SessionManager;
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
            Notify.showAlert(Alert.AlertType.ERROR, "Email or password is wrong", "Please login again!");
        } else {
            User user = UserService.instance.getUserByEmail(email);
            SessionManager.getInstance().setLoggedInUser(user);
            if (user.getRole().equals("admin")) {
                System.out.println("Đăng nhập thành công với email: " + email + " với vai trò Admin.");
                loadScene("Admin.fxml"); // Chuyển đến trang Admin
            }else if (user.getRole().equals("client")) {
                System.out.println("Đăng nhập thành công với email: " + email + " với vai trò Client.");
                loadScene("Client.fxml"); // Chuyển đến trang Clien
            }
        }
    }
    private void be_signUp() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmpassword = confirmpassField.getText();
        boolean isEmailExists = UserService.instance.isEmailExists(email);
        if (email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()) {
            Notify.showAlert(Alert.AlertType.ERROR, "Missing Information", "Please fill in all fields!");
        } else if (isEmailExists) {
            Notify.showAlert(Alert.AlertType.ERROR, "Email is existed", "Please register with other email!");
        } else if (Objects.equals(password, confirmpassword)) {
            User user = new User(email, password);
            UserService.instance.createUser(user);
            Notify.showAlert(Alert.AlertType.INFORMATION, "Register Successful", "Please Login!");
            loadScene("Login.fxml");
        } else {
            Notify.showAlert(Alert.AlertType.ERROR, "Wrong Password Matching", "Please register again!");
        }
    }
}
