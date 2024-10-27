package com.example.JFX_Controller.Admin;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

public class DocRowController {
    @FXML private Label docId;
    @FXML private Label title;
    @FXML private Label author;
    @FXML private Label genre;
    @FXML private Label amount;

    @FXML private StackPane copiesDocPane;
    
    @FXML
    void showAllCopy(MouseEvent event) {
        copiesDocPane.setVisible(true);
    }

    public void setInfo(int docId, String title, String author, String genre, int amount, StackPane copiesDocPane) {
        this.docId.setText(String.valueOf(docId));
        this.title.setText(title);
        this.author.setText(author);
        this.genre.setText(genre);
        this.amount.setText(String.valueOf(amount));
        this.copiesDocPane = copiesDocPane;
    }
}
