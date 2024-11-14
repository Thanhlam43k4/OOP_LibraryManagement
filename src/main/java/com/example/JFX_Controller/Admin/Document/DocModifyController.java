package com.example.JFX_Controller.Admin.Document;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import com.example.Model.Document;

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
        docPane.getChildren().remove(this.docModifyRoot);
        this.docModifyRoot = null;
    }

    @FXML
    void saveModifyDoc(ActionEvent event) {
        docPane.getChildren().remove(this.docModifyRoot);
        this.docModifyRoot = null;
    }

    public void setInfo(Document d, AnchorPane docPane) {
        this.title.setText(d.getTitle());
        this.author.setText(d.getAuthor());
        this.genre.setText(d.getGenre());
        this.imageUrl.setText(d.getUrlImage());
        this.isbn.setText(d.getISBN());
        this.quantity.setText(String.valueOf(d.getNumberCopy()));
        this.docPane = docPane;
    }
}