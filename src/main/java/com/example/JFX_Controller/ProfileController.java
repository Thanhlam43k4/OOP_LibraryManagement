package com.example.JFX_Controller;

import com.example.Model.User;
import com.example.Service.SessionManager;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ProfileController extends Controller {
    @FXML private Label userName;
    @FXML private Label email;
    @FXML private Label phone;
    @FXML private Label age;

    //Modify pofile
    @FXML private StackPane profilePane;
    @FXML private TextField userNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private DatePicker dobPicker;

    AnchorPane mainRoot;
    Parent profileRoot;

    @FXML
    void backToMain(ActionEvent event) {
        mainRoot.getChildren().remove(profileRoot);
        profileRoot = null;
    }
    @FXML
    void goModify(ActionEvent event) {
        profilePane.setVisible(true);
    }
    @FXML
    void cancelModify(ActionEvent event) {
        profilePane.setVisible(false);
    }
    @FXML
    void applyModify(ActionEvent event) {
        profilePane.setVisible(false);
    }

    public void setInfo(AnchorPane mainRoot, Parent profileRoot) {
        this.mainRoot = mainRoot;
        this.profileRoot = profileRoot;
        User user = SessionManager.getInstance().getLoggedInUser();
        userName.setText(user.getUsername());
        email.setText(user.getEmail());
        phone.setText(user.getPhoneNumber());
        age.setText(String.valueOf(user.getAge()));
    }
}
