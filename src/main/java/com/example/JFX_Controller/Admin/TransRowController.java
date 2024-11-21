package com.example.JFX_Controller.Admin;

import com.example.Model.Transaction;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TransRowController {
    @FXML private Label transId;
    @FXML private Label userId;
    @FXML private Label docISBN;
    @FXML private Label borrowDate;
    @FXML private Label dueDate;
    @FXML private FontAwesomeIconView iconState;
    @FXML private Label returnDate;

    public void setInfo(Transaction trans) {
        transId.setText(String.valueOf(trans.getTransactionId()));
        userId.setText(String.valueOf(trans.getUserId()));
        docISBN.setText(String.valueOf(trans.getISBN()));
        borrowDate.setText(trans.getBorrowedDate().toString());
        dueDate.setText(trans.getReturnDate().toString());
        try {
            returnDate.setText(trans.getActualReturnDate().toString());
            returnDate.setStyle("-fx-text-fill: #317c47;");
            iconState.setStyle("-fx-fill: #317c47;");
        } catch (Exception e) {
            returnDate.setText("Borrowing");
            returnDate.setStyle("-fx-text-fill: #9d3838;");
            iconState.setStyle("-fx-fill: #9d3838;");
        }
    }
}
