package com.example.JFX_Controller.Admin;

import com.example.Model.Transaction;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TransRowController {
    @FXML
    private Label transId;
    @FXML
    private Label userId;
    @FXML
    private Label docISBN;
    @FXML
    private Label borrowDate;
    @FXML
    private Label dueDate;
    @FXML
    private Label returnDate;

    public void setInfo(Transaction trans) {
        transId.setText(String.valueOf(trans.getTransactionId()));
        userId.setText(String.valueOf(trans.getUserId()));
        docISBN.setText(String.valueOf(trans.getISBN()));
        try {
            borrowDate.setText(trans.getBorrowedDate().toString());
            dueDate.setText(trans.getReturnDate().toString());
            returnDate.setText(trans.getActualReturnDate().toString());
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }
}
