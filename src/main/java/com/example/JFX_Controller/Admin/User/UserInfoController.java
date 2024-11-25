package com.example.JFX_Controller.Admin.User;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import com.example.JFX_Controller.Admin.AdminController;
import com.example.Model.Client;

import javafx.event.ActionEvent;

/**
 * Controller class responsible for displaying detailed information about a user (client) in the admin panel.
 * It shows the user's ID, name, email, phone number, age, and the number of borrowed books.
 */
public class UserInfoController {

    @FXML private Label userId;        // Label to display the user's ID
    @FXML private Label userName;      // Label to display the user's name
    @FXML private Label email;         // Label to display the user's email
    @FXML private Label phone;         // Label to display the user's phone number
    @FXML private Label age;           // Label to display the user's age
    @FXML private Label borrowed;      // Label to display the number of borrowed books by the user

    @FXML private StackPane userInfoRoot; // The root pane that holds the user info UI

    /**
     * This method is triggered when the user clicks the "Turn Off" button.
     * It removes the user info pane from the UI.
     *
     * @param event The action event triggered by the button click
     */
    @FXML
    void turnOffPane(ActionEvent event) {
        // Remove the user info root pane from the admin panel's user pane
        AdminController.instance.userPane.getChildren().remove(this.userInfoRoot);
        this.userInfoRoot = null;  // Set the root pane reference to null
    }

    /**
     * This method sets the user's information (ID, username, email, phone, age, and borrowed books)
     * to the respective labels in the user info UI.
     *
     * @param client The client whose information is being displayed
     */
    public void setInfo(Client client) {
        // Set the values for each label using the provided client's data
        this.userId.setText(String.valueOf(client.getId()));
        this.userName.setText(client.getUsername());
        this.email.setText(client.getEmail());
        this.phone.setText(client.getPhoneNumber());
        this.age.setText(String.valueOf(client.getAge()));
        this.borrowed.setText(String.valueOf(client.getBorrowedBook()));
    }
}
