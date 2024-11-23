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

public class BorrowController {
    @FXML private TextField isbn; // của copy
    
    @FXML private StackPane userBorrowRoot;

    private Client client;
    private UserRowController userRowController;
    @FXML
    void borrowDoc(ActionEvent event) {
        String isbn_input = isbn.getText();
        System.out.print(isbn_input);
        if (Validate.isValidISBN(isbn_input)) {
            Notify.showAlert(Alert.AlertType.ERROR, "Eror", "ISBN invalid!");
            return;
        }else if (!DocumentService.instance.isBookAvailable(isbn_input)){
            System.out.println(isbn_input);
            System.out.println(DocumentService.instance.isBookAvailable(isbn_input));
            Notify.showAlert(Alert.AlertType.ERROR,"Error","This Books is not available please choose other copies");
        }else if(UserService.instance.getUserBooks(client.getId()) >= 8) {
            Notify.showAlert(Alert.AlertType.ERROR,"Error","This user has more books than allowed limit!!");
        } else{
            TransactionService.instance.borrowBook(client.getId(), isbn_input);
            Notify.showAlert(Alert.AlertType.INFORMATION, "Nofication", "Borrow Document sucess!");
            // update transaction UI
            client.setBorrowedBook(client.getBorrowedBook() + 1);
            userRowController.modifyBorrowed();
            resetTrans();
            AdminController.instance.userPane.getChildren().remove(this.userBorrowRoot);
            this.userBorrowRoot = null;
        }

    }

    @FXML
    void turnOffPane(ActionEvent event) {
        AdminController.instance.userPane.getChildren().remove(this.userBorrowRoot);
        this.userBorrowRoot = null;
    }

    private void resetTrans() {
        AdminController.instance.addTranscNodes();
    }

    public void setInfo(Client client, UserRowController userRowController) {
        this.client = client;
        this.userRowController = userRowController;
    }
}