package com.example.JFX_Controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.Handlers.Notify;
import com.example.Handlers.Validate;
import com.example.Model.User;
import com.example.Service.SessionManager;

import com.example.Service.UserService;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ProfileController extends Controller implements Initializable  {
    private Parent root;
    @FXML
    private Label userName;
    @FXML
    private Label email;
    @FXML
    private Label phone;
    @FXML
    private Label age;

    // Modify pofile
    @FXML
    private StackPane profilePane;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField ageField;

    // Change password
    @FXML
    private StackPane passPane;
    @FXML
    private PasswordField oldPassword;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField confirmNewPassword;

    AnchorPane mainRoot;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = SessionManager.getInstance().getLoggedInUser();
        userName.setText(user.getUsername());
        phone.setText(user.getPhoneNumber());
        age.setText(String.valueOf(user.getAge()));
    }

    @FXML
    void backToMain(ActionEvent event) {
        mainRoot.getChildren().remove(root);
        root = null;
    }

    // Modify event
    @FXML
    void goModify(ActionEvent event) {
        setModifyPane();
    }

    @FXML
    void cancelModify(ActionEvent event) {
        profilePane.setVisible(false);
    }

    @FXML
    void applyModify(ActionEvent event) {
        if (validate()) {
            profilePane.setVisible(false);
            int userId = SessionManager.getInstance().getLoggedInUser().getId();
            UserService.instance.updateUser(userId, userNameField.getText(), phoneField.getText(), Integer.parseInt(ageField.getText()));
            userName.setText(userNameField.getText());
            phone.setText(phoneField.getText());
            age.setText(ageField.getText());
        }

    }

    // Change password event
    @FXML
    void goChangePass(ActionEvent event) {
        passPane.setVisible(true);
    }

    @FXML
    void cancelChangePass(ActionEvent event) {
        passPane.setVisible(false);
    }

    @FXML
    void applyChangePass(ActionEvent event) {
        // Check Current password is right use
        String newPass = newPassword.getText();
        String oldPass = oldPassword.getText();
        String confirmPass = confirmNewPassword.getText();

        if(!Validate.isValidPassword(newPass)){
            Notify.showAlert(Alert.AlertType.ERROR, "Password syntax error", "Please fill password that has 6 digest!");

        }else if(!UserService.instance.isMatchAccount(SessionManager.getInstance().getLoggedInUser().getEmail(),oldPass)){
            Notify.showAlert(Alert.AlertType.ERROR, "Current Password is not match", "Please try again!!!");

        }else if(newPass.equals(confirmPass)){
            int userId = SessionManager.getInstance().getLoggedInUser().getId();
            UserService.instance.updatePassword(userId,newPass);
            Notify.showAlert(Alert.AlertType.INFORMATION, "Update Password Successful", "Please login with your updated Password");
            passPane.setVisible(false);
        }else{
            Notify.showAlert(Alert.AlertType.ERROR, "Password and Confirm are not matched!!", "Please try again");

        }
        // Check password == retype password

        // Update new password after checkin
        // UserService.instance.updatePassword(userId,updatePassword);

        passPane.setVisible(false);
    }

    public void setInfo(AnchorPane mainRoot, Parent profileRoot) {
        this.mainRoot = mainRoot;
        this.root = profileRoot;
        User user = SessionManager.getInstance().getLoggedInUser();
        userName.setText(user.getUsername());
        email.setText(user.getEmail());
        phone.setText(user.getPhoneNumber());
        age.setText(String.valueOf(user.getAge()));
    }

    private void setModifyPane() {
        profilePane.setVisible(true);
        User user = SessionManager.getInstance().getLoggedInUser();
        userNameField.setText(user.getUsername());
        emailField.setText(user.getEmail());
        phoneField.setText(user.getPhoneNumber());
    }

    private boolean validate() {
        if (!Validate.isValidUsername(userNameField.getText())) {
            Notify.showAlert(Alert.AlertType.ERROR, "Eror", "Username invalid!");
            return false;
        }
        if (!Validate.isValidPhoneNumber(phoneField.getText())) {
            Notify.showAlert(Alert.AlertType.ERROR, "Eror", "Phone number invalid!");
            return false;
        }
        if (!Validate.isValidAge(ageField.getText())){
            Notify.showAlert(Alert.AlertType.ERROR, "Eror", "Age number invalid!");
            return false;
        }
        return true;
    }
}
