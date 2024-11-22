package com.example.JFX_Controller;

import com.example.Model.User;
import com.example.Service.SessionManager;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class ProfileController extends Controller {
    @FXML private Label userName;
    @FXML private Label email;
    @FXML private Label phone;
    @FXML private Label age;

    AnchorPane mainRoot;
    Parent profileRoot;

    @FXML
    void backToMain(ActionEvent event) {
        mainRoot.getChildren().remove(profileRoot);
        profileRoot = null;
    }
    @FXML
    void signOut(ActionEvent event) {
        SessionManager.getInstance().clearSession();
        loadScene("Login.fxml");
    }
    @FXML
    void toMyDoc(ActionEvent event) {

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
