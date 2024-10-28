package com.example.JFX_Controller.Admin;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.control.Label;

// Controller của từng hàng trong tab DocManager
public class DocRowController {
    @FXML private Label docId;
    @FXML private Label title;
    @FXML private Label author;
    @FXML private Label genre;
    @FXML private Label amount;

    @FXML private StackPane copiesDocPane;
    @FXML private VBox docCopyVbox;
    
    @FXML
    void showAllCopy(MouseEvent event) {
        copiesDocPane.setVisible(true);
        docCopyVbox.getChildren().clear();
        docCopyVbox.setPrefHeight(8 * 70);
        for (int i=0; i<8; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Admin/DocCopyRow.fxml"));
                Node node = loader.load();
                docCopyVbox.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }   
        }

    }

    public void setInfo(int docId, String title, String author, String genre, int amount, StackPane copiesDocPane, VBox docCopyVBox) {
        this.docId.setText(String.valueOf(docId));
        this.title.setText(title);
        this.author.setText(author);
        this.genre.setText(genre);
        this.amount.setText(String.valueOf(amount));
        this.copiesDocPane = copiesDocPane;
        this.docCopyVbox = docCopyVBox;
    }
}
