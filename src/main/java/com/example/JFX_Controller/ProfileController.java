package com.example.JFX_Controller;

import java.sql.Date;

import com.example.Handlers.Notify;
import com.example.Handlers.Validate;
import com.example.Model.User;
import com.example.Service.SessionManager;

import com.example.Service.UserService;

import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ProfileController extends Controller {
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
    private DatePicker dobPicker;

    // Change password
    @FXML
    private StackPane passPane;
    @FXML
    private TextField oldPassword;
    @FXML
    private TextField newPassword;
    @FXML
    private TextField confirmNewPassword;

    AnchorPane mainRoot;

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

            //int userId = SessionManager.getInstance().getLoggedInUser().getId();
            //Date dob = Date.valueOf(dobPicker.getValue());
            //UserService.instance.updateUser(userId, userNameField.getText(), phoneField.getText(), dob);
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
        // UserService.instance.isMatchAccount(email,password)

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
        return true;
    }
}
