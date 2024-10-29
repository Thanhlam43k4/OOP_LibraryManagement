package com.example.JFX_Controller.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserRowController {
    @FXML private Label userId;
    @FXML private Label userName;
    @FXML private Label email;
    @FXML private Label age;
    @FXML private Label phone;
    @FXML private Label borrowed;

    @FXML
    void showInfo(ActionEvent event) {

    }
    @FXML
    void borrowDoc(ActionEvent event) {

    }
    @FXML
    void returnDoc(ActionEvent event) {

    }

    public void setInfo(int userId, String userName, String email, String age, String phone, int borrowed) {
        
    }
}
