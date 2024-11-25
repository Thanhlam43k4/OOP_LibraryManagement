package com.example.JFX_Controller;

//#region Lib

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
    private PasswordField userName;

    @FXML
    void forgotPassword(ActionEvent event) {

    }
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
        final String email = emailField.getText();
        final String password = passwordField.getText();

        AsyncTaskExecutor.executeAsync(
                // Logic chạy trong luồng nền
                new Runnable() {
                    @Override
                    public void run() {
                        if (!UserService.instance.isMatchAccount(email, password)) {
                            throw new IllegalArgumentException("Email or password is wrong");
                        }
                    }
                },
                // Logic chạy trên UI thread sau khi thành công
                new Runnable() {
                    @Override
                    public void run() {
                        User user = UserService.instance.getUserByEmail(email);
                        SessionManager.getInstance().setLoggedInUser(user);

                        if ("admin".equals(user.getRole())) {
                            System.out.println("Đăng nhập thành công với email: " + email + " với vai trò Admin.");
                            loadScene("Admin.fxml");
                        } else if ("client".equals(user.getRole())) {
                            System.out.println("Đăng nhập thành công với email: " + email + " với vai trò Client.");
                            loadScene("Client.fxml");
                        }
                    }
                },
                // Logic chạy trên UI thread nếu có lỗi
                new Runnable() {
                    @Override
                    public void run() {
                        Notify.showAlert(Alert.AlertType.ERROR, "Email or password is wrong", "Please login again!");
                    }
                }
        );
    }
    private void be_signUp() {
        final String email = emailField.getText();
        final String password = passwordField.getText();
        final String confirmpassword = confirmpassField.getText();
        final String username = userName.getText();


        AsyncTaskExecutor.executeAsync(
                // Logic chạy trong luồng nền
                new Runnable() {
                    @Override
                    public void run() {
                        if (email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty() || username.isEmpty()) {
                            throw new IllegalArgumentException("Missing Information");
                        }
                        if (UserService.instance.isEmailExists(email)) {
                            throw new IllegalArgumentException("Email is existed");
                        }
                        if (!password.equals(confirmpassword)) {
                            throw new IllegalArgumentException("Wrong Password Matching");
                        }
                        User user = new User(email, password);
                        UserService.instance.createUser(user);
                    }
                },
                // Logic chạy trên UI thread sau khi thành công
                new Runnable() {
                    @Override
                    public void run() {
                        Notify.showAlert(Alert.AlertType.INFORMATION, "Register Successful", "Please Login!");
                        loadScene("Login.fxml");
                    }
                },
                // Logic chạy trên UI thread nếu có lỗi
                new Runnable() {
                    @Override
                    public void run() {
                        Notify.showAlert(Alert.AlertType.ERROR, "Error", "An error occurred during registration");
                    }
                }
        );
    }

}
