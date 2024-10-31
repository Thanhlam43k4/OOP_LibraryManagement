package com.example.JFX_Controller.Admin;

import java.io.IOException;

import com.example.Model.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class UserRowController {
    @FXML private Label userId;
    @FXML private Label userName;
    @FXML private Label email;
    @FXML private Label age;
    @FXML private Label phone;
    @FXML private Label borrowed;

    @FXML private AnchorPane userPane;

    @FXML
    void showInfo(ActionEvent event) {
        loadInfoPane();
    }
    @FXML
    void borrowDoc(ActionEvent event) {

    }
    @FXML
    void returnDoc(ActionEvent event) {

    }

    void loadInfoPane() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/UserInfo.fxml"));
            Parent userInfoPane = loader.load();

            // lấy docPane của admin
            userPane.getChildren().add(userInfoPane);
            AnchorPane.setBottomAnchor(userInfoPane, 0.0);
            AnchorPane.setLeftAnchor(userInfoPane, 0.0);
            AnchorPane.setRightAnchor(userInfoPane, 0.0);
            AnchorPane.setTopAnchor(userInfoPane, 0.0);
            
            UserInfoController userInfoController = loader.getController();
            Client client = new Client(email.getText(), userName.getText(), phone.getText(), Integer.parseInt(age.getText()));
            client.setId(Integer.parseInt(userId.getText()));
            client.setBorrowedBook(Integer.parseInt(borrowed.getText()));

            userInfoController.setInfo(client, userPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInfo(Client client, AnchorPane userPane) {
        this.userId.setText(String.valueOf(client.getId()));
        this.userName.setText(client.getUsername());
        this.email.setText(client.getEmail());
        this.phone.setText(client.getPhoneNumber());
        this.age.setText(String.valueOf(client.getAge()));
        this.borrowed.setText(String.valueOf(client.getBorrowedBook()));
        this.userPane = userPane;
    }
}
