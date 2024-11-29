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

/**
 * Controller for the user profile screen.
 * This class handles loading and updating the user profile information,
 * as well as changing the user's password.
 *
 * It includes functionality to modify user details like username, phone number,
 * age, and password. Validations are applied before making any updates.
 */
public class ProfileController extends Controller implements Initializable {

    private Parent root;

    @FXML
    private Label userName;
    @FXML
    private Label email;
    @FXML
    private Label phone;
    @FXML
    private Label age;

    // Modify profile
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

    private AnchorPane mainRoot;

    /**
     * Initializes the profile screen by loading the current user information.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the resources are not available.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = SessionManager.getInstance().getLoggedInUser();
        userName.setText(user.getUsername());
        phone.setText(user.getPhoneNumber());
        age.setText(String.valueOf(user.getAge()));
    }

    /**
     * Handles the event of going back to the main screen.
     * Removes the current profile view from the main root.
     *
     * @param event The ActionEvent triggered by the back button.
     */
    @FXML
    void backToMain(ActionEvent event) {
        mainRoot.getChildren().remove(root);
        root = null;
    }

    /**
     * Displays the profile modification pane to allow the user to update their profile information.
     *
     * @param event The ActionEvent triggered by the modify button.
     */
    @FXML
    void goModify(ActionEvent event) {
        setModifyPane();
    }

    /**
     * Cancels the profile modification and hides the modification pane.
     *
     * @param event The ActionEvent triggered by the cancel button.
     */
    @FXML
    void cancelModify(ActionEvent event) {
        profilePane.setVisible(false);
    }

    /**
     * Applies the modifications made to the user's profile.
     * Updates the user details in the system if the validations pass.
     *
     * @param event The ActionEvent triggered by the apply button.
     */
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

    /**
     * Displays the password change pane to allow the user to update their password.
     *
     * @param event The ActionEvent triggered by the change password button.
     */
    @FXML
    void goChangePass(ActionEvent event) {
        passPane.setVisible(true);
    }

    /**
     * Cancels the password change operation and hides the password pane.
     *
     * @param event The ActionEvent triggered by the cancel button in the password change pane.
     */
    @FXML
    void cancelChangePass(ActionEvent event) {
        passPane.setVisible(false);
    }

    /**
     * Applies the password change after validating the entered passwords.
     *
     * @param event The ActionEvent triggered by the apply password button.
     */
    @FXML
    void applyChangePass(ActionEvent event) {
        String newPass = newPassword.getText();
        String oldPass = oldPassword.getText();
        String confirmPass = confirmNewPassword.getText();

        if (!Validate.isValidPassword(newPass)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Password syntax error", "Please fill password that has 6 digest!");
        } else if (!UserService.instance.isMatchAccount(SessionManager.getInstance().getLoggedInUser().getEmail(), oldPass)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Current Password is not match", "Please try again!!!");
        } else if (newPass.equals(confirmPass)) {
            int userId = SessionManager.getInstance().getLoggedInUser().getId();
            UserService.instance.updatePassword(userId, newPass);
            Notify.showAlert(Alert.AlertType.INFORMATION, "Update Password Successful", "Please login with your updated Password");
            passPane.setVisible(false);
        } else {
            Notify.showAlert(Alert.AlertType.ERROR, "Password and Confirm are not matched!!", "Please try again");
        }
    }

    /**
     * Sets the initial information for the profile screen, including loading the user data.
     *
     * @param mainRoot   The main root of the application that holds the profile screen.
     * @param profileRoot The root node of the profile screen.
     */
    public void setInfo(AnchorPane mainRoot, Parent profileRoot) {
        this.mainRoot = mainRoot;
        this.root = profileRoot;
        User user = SessionManager.getInstance().getLoggedInUser();
        userName.setText(user.getUsername());
        email.setText(user.getEmail());
        phone.setText(user.getPhoneNumber());
        age.setText(String.valueOf(user.getAge()));
    }

    /**
     * Displays the profile modification pane and pre-fills the fields with current user data.
     */
    private void setModifyPane() {
        profilePane.setVisible(true);
        User user = SessionManager.getInstance().getLoggedInUser();
        userNameField.setText(user.getUsername());
        emailField.setText(user.getEmail());
        phoneField.setText(user.getPhoneNumber());
    }

    /**
     * Validates the user input for profile modification.
     * Ensures that the username, phone number, and age are in the correct format.
     *
     * @return true if all inputs are valid, false otherwise.
     */
    private boolean validate() {
        if (!Validate.isValidUsername(userNameField.getText())) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Username invalid!");
            return false;
        }
        if (!Validate.isValidPhoneNumber(phoneField.getText())) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Phone number invalid!");
            return false;
        }
        if (!Validate.isValidAge(ageField.getText())) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "Age number invalid!");
            return false;
        }
        return true;
    }
}
