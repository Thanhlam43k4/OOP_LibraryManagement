package com.example.JFX_Controller.Admin.User;

import java.sql.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ReturnCopyRowController {
    @FXML private Label docId;
    @FXML private Label title;
    @FXML private Label isbn;
    @FXML private Label dueDate;

    private Node root;
    private VBox returnDocVBox;
    @FXML
    void applyReturn(ActionEvent event) {
        //ui
        returnDocVBox.getChildren().remove(root);
    }

    public void setInfo(int docId, String title, String isbn, Date dueDate, Node root, VBox returnDocVbox) {
        this.root = root;
        this.returnDocVBox = returnDocVbox;
        this.docId.setText(String.valueOf(docId));
        this.isbn.setText(isbn);
        this.title.setText(title);
        try {
            this.dueDate.setText(dueDate.toString());            
        } catch (Exception e) {
            System.out.println("no dueDate in returnCopyRow!");
        }
    }
}
