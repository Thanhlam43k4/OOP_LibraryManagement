package com.example.JFX_Controller.Admin;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;

public class DocModifyController {

    @FXML private TextField title;
    @FXML private TextField author;
    @FXML private TextField genre;
    @FXML private TextField quantity;
    @FXML private TextField isbn;
    @FXML private TextField imageUrl;

    @FXML private StackPane docModifyRoot;
    @FXML private AnchorPane docPane;
    @FXML
    void cancelModifyDoc(ActionEvent event) {
        docPane.getChildren().remove(docModifyRoot);
        docModifyRoot = null;
    }

    @FXML
    void saveModifyDoc(ActionEvent event) {
        docPane.getChildren().remove(docModifyRoot);
        docModifyRoot = null;
    }

    public void setInfo(int docId, String title, String author, String genre, int quantity, AnchorPane docPane) {
        this.title.setText(title);
        this.author.setText(author);
        this.genre.setText(genre);
        this.quantity.setText(String.valueOf(quantity));
        this.docPane = docPane;
    }
}