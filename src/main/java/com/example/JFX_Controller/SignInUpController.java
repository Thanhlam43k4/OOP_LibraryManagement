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

/**
 * Controller for handling user authentication, password reset, and registration actions.
 */
public class SignInUpController extends Controller {

    // Login and signup fields
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPassField;
    @FXML
    private TextField userName;

    // Reset password panes and fields
    @FXML
    private StackPane forgotPassPane;
    @FXML
    private AnchorPane fillCodePane;
    @FXML
    private AnchorPane updatePassPane;
    @FXML
    private TextField emailToReset;
    @FXML
    private TextField codeField;
    @FXML
    private PasswordField confirmNewPass;
    @FXML
    private PasswordField newPassField;

    private String code;

    /**
     * Handles the "Forgot Password" button click.
     * Makes the forgot password pane visible.
     */
    @FXML
    void forgotPassword(ActionEvent event) {
        forgotPassPane.setVisible(true);
        emailToReset.setText(null);
    }

    /**
     * Cancels the password reset process by hiding the forgot password pane.
     */
    @FXML
    void cancelResetPass(ActionEvent event) {
        forgotPassPane.setVisible(false);
    }

    /**
     * Sends a reset password code to the user's email.
     * Validates the email syntax and checks if the email exists in the system.
     */
    @FXML
    void sendCode(ActionEvent event) {
        String email = emailToReset.getText();
        if (!Validate.isValidEmail(email)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Email Syntax is Wrong!!", "Please fill the right email syntax!");
        } else if (!UserService.instance.isEmailExists(email)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Email is not existed!", "Please register with your email");
        } else {
            fillCodePane.setVisible(true);
            codeField.setText(null);
            // Send email in a background thread
            AsyncTaskExecutor.executeAsync(
                    () -> {
                        // Logic to send the reset password code via email
                        code = UserService.instance.forgotPassword(email);
                    },
                    () -> {
                        // Success logic, runs on the UI thread after email is sent
                    },
                    () -> {
                        // Failure logic, runs on the UI thread in case of error
                    }
            );
        }
    }

    /**
     * Verifies the code entered by the user.
     * If valid, it shows the update password pane.
     */
    @FXML
    void applyCode(ActionEvent event) {
        String codeFill = codeField.getText();
        if (!Validate.isValidCode(codeFill)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Code Syntax Error", "Your Code must be a number and have length = 6");
        } else if (codeFill.equals(code)) {
            fillCodePane.setVisible(false);
            updatePassPane.setVisible(true);
            code = null;
        } else {
            Notify.showAlert(Alert.AlertType.ERROR, "Code does not match!!", "Please check your code in the email again!");
        }
    }

    /**
     * Cancels the code input process by hiding the code pane.
     */
    @FXML
    void cancelFillCode(ActionEvent event) {
        fillCodePane.setVisible(false);
        code = null;
    }

    /**
     * Cancels the password reset process by hiding both the forgot password and update password panes.
     */
    @FXML
    void cancelFillPass(ActionEvent event) {
        forgotPassPane.setVisible(false);
        updatePassPane.setVisible(false);
    }

    /**
     * Applies the new password if it matches the confirmation password.
     * Updates the user's password in the system.
     */
    @FXML
    void applyFillPass(ActionEvent event) {
        String newPass = newPassField.getText();
        String confirmPass = confirmNewPass.getText();
        if (!Validate.isValidPassword(newPass)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Password syntax error", "Please fill a password that has 6 characters!");
        } else if (newPass.equals(confirmPass)) {
            User user = UserService.instance.getUserByEmail(emailToReset.getText());
            int userId = user.getId();
            UserService.instance.updatePassword(userId, newPass);
            Notify.showAlert(Alert.AlertType.INFORMATION, "Update Password Successful", "Please login with your updated password.");
            forgotPassPane.setVisible(false);
            updatePassPane.setVisible(false);
        } else {
            Notify.showAlert(Alert.AlertType.ERROR, "Password and Confirm do not match!!", "Please try again");
            confirmNewPass.setText(null);
            newPassField.setText(null);
        }
    }

    /**
     * Navigates to the login page (sign-in).
     */
    @FXML
    void returnSignIn(ActionEvent event) {
        loadScene("Login.fxml");
    }

    /**
     * Navigates to the signup page.
     */
    @FXML
    void goCreateAcc(ActionEvent event) {
        loadScene("Signup.fxml");
    }

    /**
     * Handles user sign-in.
     * Authenticates the user by checking email and password.
     */
    @FXML
    void signIn(ActionEvent event) {
        be_signIn();
    }

    /**
     * Handles user sign-up.
     * Registers the user by creating a new account.
     */
    @FXML
    void signUp(ActionEvent event) {
        be_signUp();
    }

    /**
     * Signs in the user with the provided email and password.
     * Validates the credentials and redirects to the appropriate page based on user role.
     */
    private void be_signIn() {
        String email = emailField.getText();
        String password = passwordField.getText();

        AsyncTaskExecutor.executeAsync(
                () -> {
                    if (!UserService.instance.isMatchAccount(email, password)) {
                        throw new IllegalArgumentException("Email or password is wrong");
                    }
                },
                () -> {
                    User user = UserService.instance.getUserByEmail(email);
                    SessionManager.getInstance().setLoggedInUser(user);
                    if ("admin".equals(user.getRole())) {
                        loadScene("Admin.fxml");
                    } else if ("client".equals(user.getRole())) {
                        loadScene("Client.fxml");
                    }
                },
                () -> {
                    Notify.showAlert(Alert.AlertType.ERROR, "Email or password is wrong", "Please login again!");
                }
        );
    }

    /**
     * Signs up the user by creating a new account with the provided information.
     * Verifies the email, password, and username before registering the user.
     */
    private void be_signUp() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPassField.getText();
        String username = userName.getText();

        AsyncTaskExecutor.executeAsync(
                () -> {
                    if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || username.isEmpty()) {
                        throw new IllegalArgumentException("Missing Information");
                    }
                    if (UserService.instance.isEmailExists(email)) {
                        throw new IllegalArgumentException("Email is already registered");
                    }
                    if (!password.equals(confirmPassword)) {
                        throw new IllegalArgumentException("Passwords do not match");
                    }
                    String hashedPass = ExtraFunction.encode(password);
                    User user = new User(email, username, hashedPass);
                    UserService.instance.createUser(user);
                },
                () -> {
                    Notify.showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Please login!");
                    loadScene("Login.fxml");
                },
                () -> {
                    Notify.showAlert(Alert.AlertType.ERROR, "Error", "An error occurred during registration");
                }
        );
    }
}
