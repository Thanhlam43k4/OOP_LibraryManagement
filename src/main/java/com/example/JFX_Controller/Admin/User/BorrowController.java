package com.example.JFX_Controller.Admin.User;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import com.example.Handlers.ExtraFunction;
import com.example.Handlers.Notify;
import com.example.Handlers.Validate;
import com.example.Service.TransactionService;

import javafx.event.ActionEvent;

public class BorrowController {

    @FXML private TextField docId;
    @FXML private TextField isbn; // của copy
    
    @FXML private StackPane userBorrowRoot;
    @FXML private AnchorPane userPane;

    private int userId;
    @FXML
    void borrowDoc(ActionEvent event) {
        String isbn_input = isbn.getText();
        isbn_input = ExtraFunction.extractISBN(isbn_input);
        if (!Validate.isValidISBN(isbn_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Eror", "ISBN invalid!");
            return;
        }

        TransactionService.instance.borrowBook(userId, isbn_input);
        Notify.showAlert(Alert.AlertType.INFORMATION, "Nofication", "Borrow Document sucess!");
        userPane.getChildren().remove(this.userBorrowRoot);
        this.userBorrowRoot = null;
    }

    @FXML
    void turnOffPane(ActionEvent event) {
        userPane.getChildren().remove(this.userBorrowRoot);
        this.userBorrowRoot = null;
    }

    public void setInfo(int userId, AnchorPane userPane) {
        this.userPane = userPane;
        this.userId = userId;
    }
}