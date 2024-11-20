package com.example.JFX_Controller.Admin.Document;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.example.Model.Document;
import com.example.Handlers.ImageLoader;
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
            Image image = ImageLoader.loadImage(doc.getUrlImage());
            this.docCover.setImage(image);
        } catch (Exception e) {
            System.err.println("docElement coverURL invalid! when add DocElementNode");
        }
        this.title.setText(doc.getTitle());
        this.author.setText(doc.getAuthor());
        this.isbn.setText(doc.getISBN());
    }
}