package com.example.JFX_Controller;

import com.example.Service.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ProfileController extends Controller {
    @FXML
    void backToMain(ActionEvent event) {
        loadScene("Client.fxml");
    }
    @FXML
    void signOut(ActionEvent event) {
        SessionManager.getInstance().clearSession();
        loadScene("Login.fxml");
    }
    @FXML
    void toMyDoc(ActionEvent event) {

    }
}
