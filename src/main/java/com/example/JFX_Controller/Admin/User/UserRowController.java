package com.example.JFX_Controller.Admin.User;

import java.io.IOException;
import java.util.List;

import com.example.Handlers.ExtraFunction;
import com.example.JFX_Controller.Admin.AdminController;
import com.example.Model.Client;
import com.example.Model.Document;
import com.example.Model.Transaction;
import com.example.Service.DocumentService;
import com.example.Service.TransactionService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Controller class responsible for managing the user row in the admin panel.
 * It displays user information, allows borrowing of documents, and handles the return process for borrowed documents.
 */
public class UserRowController {

    @FXML private Label userId;        // Label to display the user's ID
    @FXML private Label userName;      // Label to display the user's name
    @FXML private Label email;         // Label to display the user's email
    @FXML private Label age;           // Label to display the user's age
    @FXML private Label phone;         // Label to display the user's phone number
    @FXML private Label borrowed;      // Label to display the number of borrowed books by the user

    @FXML private StackPane returnDocPane;  // The pane to display the return document functionality
    @FXML private VBox returnDocVbox;       // VBox to hold the return document rows

    private Client client;      // Client whose information is being displayed

    /**
     * This method is triggered when the "Show Info" button is clicked.
     * It loads the user information pane to display detailed user information.
     *
     * @param event The action event triggered by the button click
     */
    @FXML
    void showInfo(ActionEvent event) {
        loadInfoPane();
    }

    /**
     * This method is triggered when the "Borrow Document" button is clicked.
     * It loads the borrowing pane to allow the admin to borrow a document for the user.
     *
     * @param event The action event triggered by the button click
     */
    @FXML
    void borrowDoc(ActionEvent event) {
        loadBorrowPane();
    }

    /**
     * This method is triggered when the "Return Document" button is clicked.
     * It loads the return document rows for each borrowed document the user has.
     *
     * @param event The action event triggered by the button click
     */
    @FXML
    void openReturnDoc(ActionEvent event) {
        addCopyNodes();
    }

    /**
     * Loads the user information pane into the admin panel.
     * It uses FXMLLoader to load the `UserInfo.fxml` scene and passes the client data to the UserInfoController.
     */
    void loadInfoPane() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/UserInfo.fxml"));
            Parent userInfoPane = loader.load();

            // Add the user info pane to the admin panel
            AdminController.instance.userPane.getChildren().add(userInfoPane);
            AnchorPane.setBottomAnchor(userInfoPane, 0.0);
            AnchorPane.setLeftAnchor(userInfoPane, 0.0);
            AnchorPane.setRightAnchor(userInfoPane, 0.0);
            AnchorPane.setTopAnchor(userInfoPane, 0.0);

            // Pass client info to the UserInfoController
            UserInfoController userInfoController = loader.getController();
            userInfoController.setInfo(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the borrow document pane into the admin panel.
     * It uses FXMLLoader to load the `UserBorrow.fxml` scene and passes the client data to the BorrowController.
     */
    void loadBorrowPane() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/UserBorrow.fxml"));
            Parent borrowPane = loader.load();

            // Add the borrow pane to the admin panel
            AdminController.instance.userPane.getChildren().add(borrowPane);
            AnchorPane.setBottomAnchor(borrowPane, 0.0);
            AnchorPane.setLeftAnchor(borrowPane, 0.0);
            AnchorPane.setRightAnchor(borrowPane, 0.0);
            AnchorPane.setTopAnchor(borrowPane, 0.0);

            // Pass client info to the BorrowController
            BorrowController borrowController = loader.getController();
            borrowController.setInfo(client, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the return document rows into the return document pane.
     * It loads all transactions for the client and creates rows for each transaction.
     */
    void addCopyNodes() {
        returnDocPane.setVisible(true);  // Show the return document pane
        returnDocVbox.getChildren().clear();  // Clear previous rows
        List<Transaction> trans = TransactionService.instance.getTransactionsByUserId(client.getId());  // Get all transactions for the client
        returnDocVbox.setPrefHeight(trans.size() * 70);  // Set the height based on the number of transactions

        // For each transaction, load the return row and set its details
        for (Transaction t : trans) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/ReturnCopyRow.fxml"));
                Node node = loader.load();

                String ISBN = ExtraFunction.extractISBN(t.getISBN());  // Extract ISBN from the transaction
                Document doc = DocumentService.instance.getDocumentByISBN(ISBN);  // Get the document details using ISBN
                ReturnCopyRowController docCopyController = loader.getController();
                docCopyController.setInfo(client, this, t.getTransactionId(), doc.getTitle(), t.getISBN(), t.getReturnDate(), node, returnDocVbox);
                returnDocVbox.getChildren().add(node);  // Add the return row to the VBox
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates the "borrowed" label to reflect the current number of books the client has borrowed.
     */
    public void modifyBorrowed() {
        this.borrowed.setText(String.valueOf(client.getBorrowedBook()));
    }

    /**
     * Sets the user information for the row controller and populates the labels with the client's data.
     *
     * @param client The client whose information is to be displayed
     */
    public void setInfo(Client client) {
        this.client = client;
        this.userId.setText(String.valueOf(client.getId()));
        this.userName.setText(client.getUsername());
        this.email.setText(client.getEmail());
        this.phone.setText(client.getPhoneNumber());
        this.age.setText(String.valueOf(client.getAge()));
        this.borrowed.setText(String.valueOf(client.getBorrowedBook()));
        this.returnDocPane = AdminController.instance.returnDocPane;
        this.returnDocVbox = AdminController.instance.returnDocVbox;
    }
}
