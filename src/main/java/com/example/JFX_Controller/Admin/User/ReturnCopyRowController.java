package com.example.JFX_Controller.Admin.User;

import java.sql.Date;

import com.example.Handlers.Notify;
import com.example.JFX_Controller.Admin.AdminController;
import com.example.Service.TransactionService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ReturnCopyRowController {
    @FXML private Label transId;
    @FXML private Label title;
    @FXML private Label isbn;
    @FXML private Label dueDate;

    private Node root;
    private VBox returnDocVBox;

    private int userId;
    @FXML
    void applyReturn(ActionEvent event) {
        TransactionService.instance.returnBook(userId, isbn.getText());
        //ui
        updateTransTable();
        returnDocVBox.getChildren().remove(root);
        Notify.showAlert(Alert.AlertType.INFORMATION, "Nofication", "Return Document sucess!");
    }

    private void updateTransTable() {
        AdminController.transList.clear();
        AdminController.instance.addTranscNodes();
        AdminController.instance.setVBox(AdminController.instance.transVBox, AdminController.transList);
    }

    public void setInfo(int userId, int transId, String title, String isbn, Date dueDate, Node root, VBox returnDocVbox) {
        this.root = root;
        this.returnDocVBox = returnDocVbox;
        this.userId = userId;
        this.transId.setText(String.valueOf(transId));
        this.isbn.setText(isbn);
        this.title.setText(title);
        try {
            this.dueDate.setText(dueDate.toString());            
        } catch (Exception e) {
            System.out.println("no dueDate in returnCopyRow!");
        }
    }
}
