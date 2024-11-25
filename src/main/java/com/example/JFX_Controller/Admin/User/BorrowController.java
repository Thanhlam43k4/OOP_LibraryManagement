package com.example.JFX_Controller.Admin.User;

import com.example.Service.DocumentService;
import com.example.Service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import com.example.Handlers.Notify;
import com.example.Handlers.Validate;
import com.example.JFX_Controller.Admin.AdminController;
import com.example.Model.Client;
import com.example.Service.TransactionService;

import javafx.event.ActionEvent;

/**
 * Controller class responsible for handling the borrowing process of documents (books)
 * by clients in the Admin interface. It validates the input, checks if the book is available,
 * and updates the client's borrowed book count after a successful transaction.
 */
public class BorrowController {

    @FXML private TextField isbn;        // Input field for the ISBN of the book being borrowed
    @FXML private StackPane userBorrowRoot; // Root container for the borrow document UI

    private Client client;               // The client who is borrowing the book
    private UserRowController userRowController; // Controller to update the user interface after borrowing

    /**
     * This method is triggered when the "Borrow" button is clicked. It validates the ISBN, checks
     * the availability of the book, and ensures the user has not exceeded the borrowing limit.
     * If all conditions are met, the book is borrowed, and the transaction is processed.
     *
     * @param event The action event triggered when the "Borrow" button is clicked
     */
    @FXML
    void borrowDoc(ActionEvent event) {
        String isbn_input = isbn.getText();
        System.out.print(isbn_input);

        // Validate ISBN format
        if (Validate.isValidISBN(isbn_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "ISBN invalid!");
            return;
        }

        // Check if the book is available
        else if (!DocumentService.instance.isBookAvailable(isbn_input)) {
            System.out.println(isbn_input);
            System.out.println(DocumentService.instance.isBookAvailable(isbn_input));
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "This book is not available. Please choose another copy.");
        }

        // Check if the user has reached the borrowing limit
        else if (UserService.instance.getUserBooks(client.getId()) >= 8) {
            Notify.showAlert(Alert.AlertType.ERROR, "Error", "This user has borrowed more books than the allowed limit!");
        } else {
            // Process the borrowing transaction
            TransactionService.instance.borrowBook(client.getId(), isbn_input);
            Notify.showAlert(Alert.AlertType.INFORMATION, "Notification", "Document borrowed successfully!");

            // Update the client's borrowed book count
            client.setBorrowedBook(client.getBorrowedBook() + 1);
            userRowController.modifyBorrowed();

            // Reset transaction UI and close the borrow pane
            resetTrans();
            AdminController.instance.userPane.getChildren().remove(this.userBorrowRoot);
            this.userBorrowRoot = null;
        }
    }

    /**
     * This method is triggered when the "Turn Off" button is clicked. It closes the borrow document
     * UI without processing any transaction.
     *
     * @param event The action event triggered when the "Turn Off" button is clicked
     */
    @FXML
    void turnOffPane(ActionEvent event) {
        AdminController.instance.userPane.getChildren().remove(this.userBorrowRoot);
        this.userBorrowRoot = null;
    }

    /**
     * This method resets the transaction UI by calling the method to add new transaction nodes.
     */
    private void resetTrans() {
        AdminController.instance.addTranscNodes();
    }

    /**
     * This method sets the client and user row controller that are used in the borrow operation.
     * It is called when the controller is initialized with specific client information.
     *
     * @param client The client who is borrowing the document
     * @param userRowController The controller responsible for updating the user row UI
     */
    public void setInfo(Client client, UserRowController userRowController) {
        this.client = client;
        this.userRowController = userRowController;
    }
}
