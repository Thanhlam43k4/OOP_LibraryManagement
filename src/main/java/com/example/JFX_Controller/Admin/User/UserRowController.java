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

public class UserRowController {
    @FXML private Label userId;
    @FXML private Label userName;
    @FXML private Label email;
    @FXML private Label age;
    @FXML private Label phone;
    @FXML private Label borrowed;

    @FXML private StackPane returnDocPane;
    @FXML private VBox returnDocVbox;

    private Client client;
    @FXML
    void showInfo(ActionEvent event) {
        loadInfoPane();
    }
    @FXML
    void borrowDoc(ActionEvent event) {
        loadBorrowPane();
    }
    @FXML
    void openReturnDoc(ActionEvent event) {
        addCopyNodes();
    }

    void loadInfoPane() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/UserInfo.fxml"));
            Parent userInfoPane = loader.load();

            // lấy docPane của admin
            AdminController.instance.userPane.getChildren().add(userInfoPane);
            AnchorPane.setBottomAnchor(userInfoPane, 0.0);
            AnchorPane.setLeftAnchor(userInfoPane, 0.0);
            AnchorPane.setRightAnchor(userInfoPane, 0.0);
            AnchorPane.setTopAnchor(userInfoPane, 0.0);
            
            UserInfoController userInfoController = loader.getController();
            userInfoController.setInfo(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadBorrowPane() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/UserBorrow.fxml"));
            Parent borrowPane = loader.load();

            // lấy docPane của admin
            AdminController.instance.userPane.getChildren().add(borrowPane);
            AnchorPane.setBottomAnchor(borrowPane, 0.0);
            AnchorPane.setLeftAnchor(borrowPane, 0.0);
            AnchorPane.setRightAnchor(borrowPane, 0.0);
            AnchorPane.setTopAnchor(borrowPane, 0.0);
            
            BorrowController borrowController = loader.getController();
            borrowController.setInfo(client, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addCopyNodes() {
        returnDocPane.setVisible(true);
        returnDocVbox.getChildren().clear();
        List<Transaction> trans = TransactionService.instance.getTransactionsByUserId(client.getId());
        returnDocVbox.setPrefHeight(trans.size() * 70);
        for (Transaction t : trans) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/ReturnCopyRow.fxml"));
                Node node = loader.load();

                String ISBN = ExtraFunction.extractISBN(t.getISBN());
                Document doc = DocumentService.instance.getDocumentByISBN(ISBN);
                ReturnCopyRowController docCopyController = loader.getController();
                docCopyController.setInfo(client, this, t.getTransactionId(), doc.getTitle(), t.getISBN(), t.getReturnDate(), node, returnDocVbox);
                returnDocVbox.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            } 
        }
    }

    public void modifyBorrowed() {
        this.borrowed.setText(String.valueOf(client.getBorrowedBook()));
    }

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
