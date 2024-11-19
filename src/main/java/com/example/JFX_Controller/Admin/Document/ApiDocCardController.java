package com.example.JFX_Controller.Admin.Document;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.example.Model.Document;

import javafx.event.ActionEvent;

public class ApiDocCardController {
    @FXML private ImageView docCover;
    @FXML private Label title;
    @FXML private Label author;
    @FXML private Label genre;
    @FXML private Label isbn;

    @FXML
    void addDocToLib(ActionEvent event) {

    }

    public void setInfo(Document doc) {
        try {
            this.docCover.setImage(new Image(doc.getUrlImage()));
        } catch (Exception e) {
            System.err.println("docElement coverURL invalid! when add DocElementNode");
        }
        this.title.setText(doc.getTitle());
        this.author.setText(doc.getAuthor());
        this.genre.setText(doc.getGenre());
    }
}