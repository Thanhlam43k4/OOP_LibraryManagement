package com.example.JFX_Controller.Admin;

import com.example.Model.Transaction;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller class for managing a single row in the transaction table.
 * It sets the transaction information into the UI elements and handles
 * visual indicators for the transaction state.
 */
public class TransRowController {

    @FXML private Label transId; /**< Label for displaying the transaction ID. */
    @FXML private Label userId; /**< Label for displaying the user ID. */
    @FXML private Label docISBN; /**< Label for displaying the document ISBN. */
    @FXML private Label borrowDate; /**< Label for displaying the borrow date. */
    @FXML private Label dueDate; /**< Label for displaying the due date. */
    @FXML private FontAwesomeIconView iconState; /**< Icon for visualizing transaction state. */
    @FXML private Label returnDate;

    /**
     * Sets transaction information into the respective UI components.
     *
     * @param trans The {@link Transaction} object containing transaction details.
     */
    public void setInfo(Transaction trans) {
        // Set the basic transaction details
        transId.setText(String.valueOf(trans.getTransactionId()));
        userId.setText(String.valueOf(trans.getUserId()));
        docISBN.setText(String.valueOf(trans.getISBN()));
        borrowDate.setText(trans.getBorrowedDate().toString());
        dueDate.setText(trans.getReturnDate().toString());

        // Set return date and style based on transaction state
        try {
            returnDate.setText(trans.getActualReturnDate().toString());
            returnDate.setStyle("-fx-text-fill: #317c47;"); // Green for returned state
            iconState.setStyle("-fx-fill: #317c47;"); // Green icon
        } catch (NullPointerException e) {
            // Handle case where the transaction is still ongoing (not returned)
            returnDate.setText("Borrowing");
            returnDate.setStyle("-fx-text-fill: #9d3838;"); // Red for borrowing state
            iconState.setStyle("-fx-fill: #9d3838;"); // Red icon
        }
    }
}
