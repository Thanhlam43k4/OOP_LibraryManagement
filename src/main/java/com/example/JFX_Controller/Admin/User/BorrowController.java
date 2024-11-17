package com.example.JFX_Controller.Admin.User;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;

public class BorrowController {

    @FXML private TextField docId;
    @FXML private TextField isbn;
    
    @FXML private StackPane userBorrowRoot;
    @FXML private AnchorPane userPane;
    @FXML
    void borrowDoc(ActionEvent event) {
        userPane.getChildren().remove(this.userBorrowRoot);
        this.userBorrowRoot = null;
    }

    @FXML
    void turnOffPane(ActionEvent event) {
        userPane.getChildren().remove(this.userBorrowRoot);
        this.userBorrowRoot = null;
    }

    public void setInfo(AnchorPane userPane) {
        this.userPane = userPane;
    }
}