package com.example.JFX_Controller;

//#region Lib

import com.example.Handlers.Notify;
import com.example.Handlers.Validate;
import com.example.Model.User;
import com.example.Service.SessionManager;
import com.example.Service.UserService;
import com.example.Handlers.ExtraFunction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
//#endregion

public class SignInUpController extends Controller {
    // login & signup
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmpassField;
    @FXML private TextField userName;
    // reset password
    @FXML  private StackPane forgotPassPane;
    @FXML private AnchorPane fillCodePane;
    @FXML private AnchorPane updatePassPane;
    @FXML private TextField emailToReset;
    @FXML private TextField codeField;
    @FXML private PasswordField confirmNewPass;
    @FXML private PasswordField newPassField;
    String code;

    // reset password
    @FXML
    void forgotPassword(ActionEvent event) {
        forgotPassPane.setVisible(true);
        emailToReset.setText(null);
    }
    @FXML
    void cancelResetPass(ActionEvent event) {
        forgotPassPane.setVisible(false);
    }
    @FXML
    void sendCode(ActionEvent event) {
        String email = emailToReset.getText();
        if(!Validate.isValidEmail(email)){
            Notify.showAlert(Alert.AlertType.ERROR, "Email Syntax is Wrong!!", "Please fill the right email syntax!");
        }else if(!UserService.instance.isEmailExists(email)){
            Notify.showAlert(Alert.AlertType.ERROR, "Email is not existed!", "Please register with your email");
        }else {
            fillCodePane.setVisible(true);
            codeField.setText(null);
            // Gửi email trong luồng nền
            AsyncTaskExecutor.executeAsync(
                    () -> {
                        // Logic gửi email
                       code =  UserService.instance.forgotPassword(email);
                    },
                    () -> {
                        // Logic khi gửi thành công, chạy trên UI Thread
//                      System.out.println("Gui email thanh cong ");
                    },
                    () -> {
                        // Logic khi gửi thất bại, chạy trên UI Thread
                        //Notify.showAlert(Alert.AlertType.ERROR, "Failed to Send Email", "An error occurred while sending the email.");
                    }
            );

        }
    }
    @FXML
    void applyCode(ActionEvent event) {

        String codeFill = codeField.getText();
        if(!Validate.isValidCode(codeFill)){
            Notify.showAlert(Alert.AlertType.ERROR, "Code Syntax Error", "Your Code must be number and has length = 6");

        }else if(codeFill.equals(code)){
            fillCodePane.setVisible(false);
            updatePassPane.setVisible(true);
            code = null;
        }else{
            Notify.showAlert(Alert.AlertType.ERROR, "Code is not match!!", "Please Check Your Code in Email Again!");
        }

    }
    @FXML
    void cancelFillCode(ActionEvent event) {
        fillCodePane.setVisible(false);
        code = null;
    }
    @FXML
    void cancelFillPass(ActionEvent event) {
        forgotPassPane.setVisible(false);
        updatePassPane.setVisible(false);
    }
    @FXML
    void applyFillPass(ActionEvent event) {
        String newPass = newPassField.getText();
        String confirmPass = confirmNewPass.getText();
        if(!Validate.isValidPassword(newPass)){
            Notify.showAlert(Alert.AlertType.ERROR, "Password syntax error", "Please fill password that has 6 digest!");

        }else if(newPass.equals(confirmPass)){

            User user = UserService.instance.getUserByEmail(emailToReset.getText());
            int userId = user.getId();
            System.out.println(userId);
            UserService.instance.updatePassword(userId,newPass);
            Notify.showAlert(Alert.AlertType.INFORMATION, "Update Password Successful", "Please login with your updated Password");
            forgotPassPane.setVisible(false);
            updatePassPane.setVisible(false);
        }else{
            Notify.showAlert(Alert.AlertType.ERROR, "Password and Confirm are not matched!!", "Please try again");
            confirmNewPass.setText(null);
            newPassField.setText(null);

        }
    }

    //End

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
         String email = emailField.getText();
         String password = passwordField.getText();
         String confirmpassword = confirmpassField.getText();
         String username = userName.getText();
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
                        System.out.println(password);
                        String hashedPass = ExtraFunction.encode(password);
                        User user = new User(email,username, hashedPass);
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
