package com.example.JFX_Controller.Admin.User;

import java.sql.Date;

import com.example.Handlers.Notify;
import com.example.JFX_Controller.Admin.AdminController;
import com.example.Service.TransactionService;
import com.example.Model.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Controller class responsible for handling the return of borrowed documents by clients.
 * It updates the client's borrowed book count and the transaction table after a successful return.
 */
public class ReturnCopyRowController {

    @FXML private Label transId;     // Transaction ID label
    @FXML private Label title;       // Book title label
    @FXML private Label isbn;        // ISBN label
    @FXML private Label dueDate;     // Due date label

    private Node root;               // The root node of the row to be removed after return
    private VBox returnDocVBox;      // VBox containing the return document rows

    private Client client;           // The client returning the document
    private UserRowController userRowController;  // Controller to update the user's borrowed book count

    /**
     * This method is triggered when the "Return" button is clicked. It processes the document return
     * by updating the client's borrowed book count and the transaction table. It also removes the
     * return document row from the UI and shows a success notification.
     *
     * @param event The action event triggered when the "Return" button is clicked
     */
    @FXML
    void applyReturn(ActionEvent event) {
        // Process the return of the book by calling the transaction service
        TransactionService.instance.returnBook(client.getId(), isbn.getText());

        // Update the UI and client data
        client.setBorrowedBook(client.getBorrowedBook() - 1);
        userRowController.modifyBorrowed();

        // Update the transaction table
        updateTransTable();

        // Remove the return document row from the UI
        returnDocVBox.getChildren().remove(root);

        // Show a success notification
        Notify.showAlert(Alert.AlertType.INFORMATION, "Notification", "Return Document successful!");
    }

    /**
     * This method updates the transaction table by adding new transaction nodes.
     */
    private void updateTransTable() {
        AdminController.instance.addTranscNodes();
    }

    /**
     * This method sets the transaction details for the return document row. It initializes the labels
     * for transaction ID, title, ISBN, and due date. It also stores the necessary UI components for
     * managing the return action.
     *
     * @param client The client who is returning the document
     * @param userRowController The controller responsible for updating the user's borrowed book count
     * @param transId The transaction ID of the borrowed document
     * @param title The title of the borrowed document
     * @param isbn The ISBN of the borrowed document
     * @param dueDate The due date of the borrowed document
     * @param root The root node of the current row in the return document UI
     * @param returnDocVbox The VBox containing all return document rows
     */
    public void setInfo(Client client, UserRowController userRowController, int transId, String title, String isbn, Date dueDate, Node root, VBox returnDocVbox) {
        this.root = root;
        this.returnDocVBox = returnDocVbox;
        this.client = client;
        this.userRowController = userRowController;

        // Set the text of the labels with transaction details
        this.transId.setText(String.valueOf(transId));
        this.isbn.setText(isbn);
        this.title.setText(title);

        // Set the due date label, handle exception if no due date is provided
        try {
            this.dueDate.setText(dueDate.toString());
        } catch (Exception e) {
            System.out.println("No dueDate in returnCopyRow!");
        }
    }
}
